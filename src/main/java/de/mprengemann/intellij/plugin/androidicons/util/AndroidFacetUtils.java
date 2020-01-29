/*
 * Copyright 2015 Marc Prengemann
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance 
 * with the License. You may obtain a copy of the License at
 *
 * 			http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed 
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for 
 * the specific language governing permissions and limitations under the License.
 */

package de.mprengemann.intellij.plugin.androidicons.util;

import com.intellij.facet.ProjectFacetManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.android.facet.AndroidFacet;
import org.jetbrains.android.facet.ResourceFolderManager;

import java.util.List;

public class AndroidFacetUtils {
    private AndroidFacetUtils() {
    }

    public static AndroidFacet getCurrentFacet(Project project, Module module) {
        AndroidFacet currentFacet = null;
        if (module == null) {
            return null;
        }
        List<AndroidFacet> applicationFacets = ProjectFacetManager.getInstance(project).getFacets(AndroidFacet.ID);
        for (AndroidFacet facet : applicationFacets) {
            if (facet.getModule().getName().equals(module.getName())) {
                currentFacet = facet;
                break;
            }
        }
        return currentFacet;
    }

    public static String getResourcesRoot(Project project, Module module) {
        final AndroidFacet currentFacet = AndroidFacetUtils.getCurrentFacet(project, module);
        //final List<VirtualFile> allResourceDirectories = currentFacet.getAllResourceDirectories();
        final List<VirtualFile> allResourceDirectories = ResourceFolderManager.getInstance(currentFacet).getFolders();
        String exportRoot = "";
        if (allResourceDirectories.size() == 1) {
            exportRoot = allResourceDirectories.get(0).getCanonicalPath();
        }

        return exportRoot;
    }

    public static void updateActionVisibility(AnActionEvent e) {
        Module module = e.getData(LangDataKeys.MODULE);
        AndroidFacet androidFacet = AndroidFacetUtils.getCurrentFacet(AnAction.getEventProject(e), module);
        e.getPresentation().setVisible(module != null && androidFacet != null);
    }
}

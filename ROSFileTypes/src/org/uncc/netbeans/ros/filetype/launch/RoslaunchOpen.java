/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uncc.netbeans.ros.filetype.launch;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.netbeans.api.project.Project;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.uncc.netbeans.ros.project.ROSProject;
import org.uncc.netbeans.ros.project.RunInNetbeansTerminal;

@ActionID(
        category = "File",
        id = "org.uncc.netbeans.ros.filetype.launch.RoslaunchOpen"
)
@ActionRegistration(
        displayName = "#CTL_RoslaunchOpen"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 0),
    @ActionReference(path = "Loaders/text/x-roslaunch+xml/Actions", position = 0)
})
@NbBundle.Messages({"CTL_RoslaunchOpen=Open with roslaunch/roscore"})
public final class RoslaunchOpen implements ActionListener {
    private static final RequestProcessor RP = new RequestProcessor("Terminal Action RP", 100); // NOI18N    

    private final LaunchDataObject context;    
    ROSProject project;
    Project projec;
    
    public RoslaunchOpen(LaunchDataObject context) {
        this.projec = context.getLookup().lookup(Project.class);
//        Node n = DataObject.find(pf).getNodeDelegate();
//        while (n.getParentNode() != null) {
//            n = n.getParentNode();
//            Project p = n.getLookup().lookup(Project.class);
//            if (p != null) {
//                break;
//            }
//        }
//        Project proj = pf.getLookup().lookup(Project.class);
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        String[] commandList;
        String rosRootFolder = project.getProperty("ros.root");
        String wsFolder = project.getProperty("ros.ws");
        String wsInstallFolder = project.getProperty("ros.ws.install");
//        String wsSrcFolder = project.getProperty("ros.ws.install");
        String installSetupPath=".";
        if (project.getProjectDirectory().getFileObject(wsFolder).
                getFileObject(wsInstallFolder).getFileObject("setup.bash") != null) {
            
        } else {
            // abort the run --> no install directory available
            installSetupPath = project.getProjectDirectory().getPath()+"/"
                    +wsFolder+"/"+wsInstallFolder+"/";
        }
        String projfolder = project.getProjectDirectory().getPath();
        
        String homeDir = context.getPrimaryFile().getParent().getPath();
        String actionName = "roscore";
//        commandList = new String[]{
//            "source /opt/ros/indigo/setup.bash\n",
//            "roscore\n",
//            "exit"
//        };
//        RunInNetbeansTerminal.runInNewTerminal(actionName, homeDir, commandList);
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//            Exceptions.printStackTrace(ex);
//        }
        actionName = "roslaunch";
        String launchfilename = context.getPrimaryFile().getNameExt();
        commandList = new String[]{
            "source "+rosRootFolder+"setup.bash\n",
            "source "+installSetupPath+"setup.bash\n",
            "cd "+homeDir+"\n",
            "roslaunch "+launchfilename+"\n",
            "exit"
        };
        String tabName = actionName + " " + ev.getSource().toString();
        RunInNetbeansTerminal.runInNewTerminal(actionName, homeDir, commandList);
    }
}

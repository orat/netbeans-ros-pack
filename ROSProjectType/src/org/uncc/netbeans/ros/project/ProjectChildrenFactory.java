/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.uncc.netbeans.ros.project;

import org.netbeans.api.project.Project;
import org.openide.loaders.DataObjectNotFoundException;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;
import org.openide.nodes.NodeMemberEvent;
import org.openide.nodes.NodeReorderEvent;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.lookup.Lookups;
import org.openide.util.lookup.ProxyLookup;
import org.uncc.netbeans.ros.project.ws.MakeProjectFilterNode;

/**
 *
 * @author arwillis
 */
public class ProjectChildrenFactory extends FilterNode.Children {
    Project p;

    public ProjectChildrenFactory(Node or) {
        super(or);
    }

    public ProjectChildrenFactory(Project p, Node or) {
        this(or);
        this.p = p;
    }

    @Override
    protected void addNotify() {
        super.addNotify();
    }

    @Override
    protected void filterChildrenAdded(NodeMemberEvent nme) {
        super.filterChildrenAdded(nme);
    }

    @Override
    protected void filterChildrenRemoved(NodeMemberEvent nme) {
        super.filterChildrenRemoved(nme);
    }

    @Override
    protected void filterChildrenReordered(NodeReorderEvent nre) {
        super.filterChildrenReordered(nre);
    }

    @Override
    protected Node copyNode(Node n) {
//        n.addNodeListener((ROSProject)p);
        if (n.getName().equals("nbproject")) {
        }
        if (n.getName().equals("ros_ws")) {
            try {
                FilterNode fn = new MakeProjectFilterNode(n, p);
                return fn;
            } catch (DataObjectNotFoundException ex) {
                Exceptions.printStackTrace(ex);
            }
        } else {
            FilterNode fn = new FilterNode(n, new FilterNode.Children(n),
                    new ProxyLookup(new Lookup[]{
                        Lookups.singleton(p),
                        n.getLookup()}));
            return fn;
        }
        return n.cloneNode();
    }

    @Override
    protected Node[] createNodes(Node n) {
        Node[] nodeArray = super.createNodes(n);
        if (nodeArray[0].getName().equals("nbproject")) {
            return null;
        }
        return nodeArray;
    }
}
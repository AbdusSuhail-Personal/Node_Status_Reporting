package com.bt.evaluation.Application;

import com.bt.evaluation.Domain.Node;
import com.bt.evaluation.Domain.NodeState;
import com.bt.evaluation.Domain.NotificationType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NodeStatusApplication {

    public static void main(String[] args) throws IOException {
        File notificationRecordFile;
        List<Node> nodeList = new ArrayList<Node>();
        try {
            if (0 < args.length) {
                String filename = args[0];
                notificationRecordFile = new File(filename);
                Scanner sc = new Scanner(notificationRecordFile); //Better business reason to use Scanner rather than a BufferedReader
                while (sc.hasNextLine()) {
                    String line = sc.nextLine();
                    String[] lineParts = line.split("[ \\t]"); //Parses the notification line by space or tabs

                    //If NotificationType is HELLO
                    if (lineParts[3].equals(NotificationType.HELLO.toString())) {
                        Node node = new Node(lineParts[0], lineParts[1], lineParts[2], NodeState.ALIVE, lineParts[2] + " " + lineParts[3]);

                        //Add Or Update node in List
                        if (nodeList.stream().noneMatch(n -> n.getName().equals(node.getName()))) {
                            nodeList.add(node);
                        } else {
                            nodeList.forEach(n -> {
                                if (n.getName().equals(node.getName())) {
                                    n.setNodeTime(node.getNodeTime());
                                    n.setMonitoringTime(node.getMonitoringTime());
                                    n.setNodeState(NodeState.ALIVE);
                                    n.setLastEventDetail(lineParts[2] + " " + lineParts[3]);
                                }
                            });
                        }

                    }

                    //If NotificationType is FOUND
                    if (lineParts[3].equals(NotificationType.FOUND.toString())) {
                        Node sourceNode = new Node(lineParts[0], lineParts[1], lineParts[2], NodeState.ALIVE, lineParts[2] + " " + lineParts[3] + " " + lineParts[4]);
                        Node destinationNode = new Node(lineParts[0], lineParts[1], lineParts[4], NodeState.ALIVE, lineParts[2] + " " + lineParts[3] + " " + lineParts[4]);

                        //Add Or Update Source Node in List
                        if (nodeList.stream().noneMatch(n -> n.getName().equals(sourceNode.getName()))) {
                            nodeList.add(sourceNode);
                        } else {
                            nodeList.forEach(n -> {
                                if (n.getName().equals(sourceNode.getName())) {
                                    n.setNodeTime(sourceNode.getNodeTime());
                                    n.setMonitoringTime(sourceNode.getMonitoringTime());
                                    n.setNodeState(NodeState.ALIVE);
                                    n.setLastEventDetail(lineParts[2] + " " + lineParts[3] + " " + lineParts[4]);
                                }
                            });
                        }


                        //Add Or Update Destination Node in List
                        if (nodeList.stream().noneMatch(n -> n.getName().equals(destinationNode.getName()))) {
                            nodeList.add(destinationNode);
                        } else {
                            nodeList.forEach(n -> {
                                        if (n.getName().equals(destinationNode.getName())) {
                                            n.setNodeTime(destinationNode.getNodeTime());
                                            n.setMonitoringTime(destinationNode.getMonitoringTime());
                                            n.setNodeState(NodeState.ALIVE);
                                            n.setLastEventDetail(lineParts[2] + " " + lineParts[3] + " " + lineParts[4]);
                                        }
                                    }
                            );

                        }
                    }

                    //If NotificationType is LOST
                    if (lineParts[3].equals(NotificationType.LOST.toString())) {
                        Node sourceNode = new Node(lineParts[0], lineParts[1], lineParts[2], NodeState.ALIVE, lineParts[2] + " " + lineParts[3] + " " + lineParts[4]);
                        Node destinationNode = new Node(lineParts[0], lineParts[1], lineParts[4], NodeState.DEAD, lineParts[2] + " " + lineParts[3] + " " + lineParts[4]);

                        //Add Or Update Source Node in List
                        if (nodeList.stream().noneMatch(n -> n.getName().equals(sourceNode.getName()))) {
                            nodeList.add(sourceNode);
                        } else {
                            nodeList.forEach(n -> {
                                        if (n.getName().equals(sourceNode.getName())) {
                                            n.setNodeTime(sourceNode.getNodeTime());
                                            n.setMonitoringTime(sourceNode.getMonitoringTime());
                                            n.setNodeState(NodeState.ALIVE);
                                            n.setLastEventDetail(lineParts[2] + " " + lineParts[3] + " " + lineParts[4]);
                                        }
                                    }
                            );
                        }
                        //Add Or Update Destination Node in List
                        if (nodeList.stream().noneMatch(n -> n.getName().equals(destinationNode.getName()))) {
                            nodeList.add(destinationNode);
                        } else {
                            nodeList.forEach(n -> {
                                if (n.getName().equals(destinationNode.getName())) {
                                    n.setNodeTime(destinationNode.getNodeTime());
                                    n.setMonitoringTime(destinationNode.getMonitoringTime());
                                    n.setNodeState(NodeState.DEAD);
                                    n.setLastEventDetail(lineParts[2] + " " + lineParts[3] + " " + lineParts[4]);
                                }
                            });

                        }
                    }
                }
            }
            //Print Output
            nodeList.forEach(n -> System.out.println(n.printNodeStatus()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

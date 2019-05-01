package ie.dylangore.dsa2.ca2.graph;

import ie.dylangore.dsa2.ca2.data.RouteManager;
import ie.dylangore.dsa2.ca2.types.Link;
import ie.dylangore.dsa2.ca2.types.Marker;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles graph searching and route calculation
 */
public class RouteCalculater {

    /**
     * Graph depth first search
     * @param from start point
     * @param encountered list of already encountered nodes
     * @param lookingfor end point
     * @return marker
     */
    public static Marker searchGraphDepthFirst(Marker from, List<Marker> encountered, Marker lookingfor) {
        System.out.println(from);
        if (from.equals(lookingfor))
            return from;
        if (encountered == null)
            encountered = new ArrayList<>(); // First node so create new (empty) encountered list
        encountered.add(from);
        for (Marker adjNode : from.getNearby())
            if (!encountered.contains(adjNode)) {
                Marker result = searchGraphDepthFirst(adjNode, encountered, lookingfor);
                if (result != null)
                    return result;
            }
        return null;
    }

    /**
     * Generate a path with a value depending on different factors
     */
    public static class CostedPath {
        private double pathCost = 0;
        private List<Marker> pathList = new ArrayList<>();

        public String toString() {
            return "Path: " + pathList;
        }

        public List<Marker> getPathList(){
            return this.pathList;
        }

        public double getPathCost() {
            return pathCost;
        }

        void setPathCost(double pathCost) {
            this.pathCost = pathCost;
        }
    }

    /**
     * Find the 'best value' path based on different criteria
     * @param startNode node to start at
     * @param lookingfor end point
     * @param method value criteria (shortest, easiest, safest)
     * @return path
     */
    public static CostedPath findCheapestPathDijkstra(Marker startNode, Marker lookingfor, String method){
        CostedPath cp=new CostedPath(); //Create result object for cheapest path
        List<Marker> encountered;
        List<Marker> unencountered = new ArrayList<>(); //Create encountered/unencountered lists
        List<Marker> waypoints = RouteManager.getWaypoints();
        startNode.nodeValue=0; //Set the starting node value to zero
         //Add the start node as the only value in the unencountered list to start
        unencountered.add(startNode);

        // Handle avoiding cities
        if(RouteManager.getAvoidList() != null){
            encountered = new ArrayList<>(RouteManager.getAvoidList());
        }else{
            encountered = new ArrayList<>();
        }

        Marker currentNode;
        do{ //Loop until unencountered list is empty
            currentNode=unencountered.remove(0); //Get the first unencountered node (sorted list, so will have lowest value)
            encountered.add(currentNode); //Record current node in encountered list
            if(currentNode.equals(lookingfor)){ //Found goal - assemble path list back to start and return it
                cp.pathList.add(currentNode); //Add the current (goal) node to the result list (only element)
                cp.setPathCost(currentNode.nodeValue); //The total cheapest path cost is the node value of the current/goal node
                while(currentNode!=startNode) { //While we're not back to the start node...
                    boolean foundPrevPathNode=false; //Use a flag to identify when the previous path node is identified
                    for(Marker n : encountered) { //For each node in the encountered list...
                        for(Link e : n.getLinks()){
                            int modifier = 0;
                            if(method.equals("easiest")){
                                modifier = e.getEase();
                            }else if(method.equals("safest")){
                                modifier = e.getSafety();
                            }else{
                                modifier = e.getDistance();
                            }
                            if ((e.getEnd() == currentNode || e.getStart() == currentNode) && currentNode.nodeValue - modifier == n.nodeValue) {
                                //current node and the difference in node values is the cost of the edge -> found path node!
                                cp.pathList.add(0,n); //Add the identified path node to the front of the result list
                                currentNode=n; //Move the currentNode reference back to the identified path node
                                foundPrevPathNode=true; //Set the flag to break the outer loop
                                break; //We've found the correct previous path node and moved the currentNode reference
                                //back to it so break the inner loop
                            }
                        }
                        if(foundPrevPathNode) break; //We've identified the previous path node, so break the inner loop to continue
                    }
                }
                //Reset the node values for all nodes to (effectively) infinity so we can search again (leave no footprint!)
                for(Marker n : encountered) n.nodeValue=Integer.MAX_VALUE;
                for(Marker n : unencountered) n.nodeValue=Integer.MAX_VALUE;
                return cp; //The costed (cheapest) path has been assembled, so return it!
            }
            //We're not at the goal node yet, so...
            for(Link e : currentNode.getLinks()){
                int modifier = 0;
                if(method.equals("easiest")){
                    modifier = e.getEase();
                }else if(method.equals("safest")){
                    modifier = e.getSafety();
                }else{
                    modifier = e.getDistance();
                }

                if(!encountered.contains(e.getEnd())) { //If the node it leads to has not yet been encountered (i.e. processed)
                    e.getEnd().nodeValue=Integer.min(e.getEnd().nodeValue, currentNode.nodeValue+modifier); //Update the node value at the end
                    //of the edge to the minimum of its current value or the total of the current node's value plus the cost of the edge
                    unencountered.add(e.getEnd());
                }
            }

            unencountered.sort((n1, n2) -> n1.nodeValue - n2.nodeValue); //Sort in ascending node value order
        }while(!unencountered.isEmpty());
        return null; //No path found, so return null
    }
}

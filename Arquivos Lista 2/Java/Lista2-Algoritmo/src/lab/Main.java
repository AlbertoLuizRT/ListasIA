package lab;


import java.util.List;

import aima.core.agent.Action;
import aima.core.environment.map.Map;
import aima.core.environment.map.MapFunctionFactory;
import aima.core.environment.map.MapStepCostFunction;
import aima.core.environment.map.MoveToAction;
import aima.core.environment.map.StraightLineDistanceHeuristicFunction;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.problem.DefaultGoalTest;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.TreeSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.search.uninformed.UniformCostSearch;

public class Main {
	
	public static void printSolution(SearchAgent agent, String start, Map map) {
		
		List<Action> list = agent.getActions();
		
		float cost = 0;
		String city = start;
		
		System.out.println(start);
		
		for (Action a : list) {
			String location = ((MoveToAction)a).getToLocation();
			cost += map.getDistance(city, location);
			city = location;
			System.out.println(location + " | Total cost: " + cost);
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		Map romaniaMap = new SimplifiedRoadMapOfPartOfRomania();
		
		
		Object start = SimplifiedRoadMapOfPartOfRomania.ARAD;
		Object end   = SimplifiedRoadMapOfPartOfRomania.BUCHAREST;
		
		Problem problem = new Problem(start, 
									MapFunctionFactory.getActionsFunction(romaniaMap),
									MapFunctionFactory.getResultFunction(), 
									new DefaultGoalTest(end), 
									new MapStepCostFunction(romaniaMap));

		
		Search breadth_first_tree_search = new BreadthFirstSearch();
		Search depth_first_graph_search = new DepthFirstSearch(new GraphSearch());	
		Search best_first_graph_search = new GreedyBestFirstSearch(new TreeSearch(),
				new StraightLineDistanceHeuristicFunction(end, romaniaMap));
		
		Search depth_limited_search = new DepthLimitedSearch(3); // change it!
		
		Search uniform_cost_search = new UniformCostSearch();
		
		Search iterative_deepening_search =new IterativeDeepeningSearch();
		
		HeuristicFunction hf = new HeuristicFunction() {
			public double h(Object state) {
				return 0; // Don't have one for this lab!
			}		};
		Search astar_search = new AStarSearch(new GraphSearch(), hf);
		
		
		SearchAgent solution1 = new SearchAgent(problem, breadth_first_tree_search);
		SearchAgent solution2 = new SearchAgent(problem, depth_first_graph_search);
		SearchAgent solution3 = new SearchAgent(problem, depth_limited_search);
		SearchAgent solution4 = new SearchAgent(problem, uniform_cost_search);
		SearchAgent solution5 = new SearchAgent(problem, iterative_deepening_search);
		SearchAgent solution6 = new SearchAgent(problem, best_first_graph_search);
		SearchAgent solution7 = new SearchAgent(problem, astar_search);
		
		printSolution(solution1, start.toString(), romaniaMap);
		System.out.println("\n\n\n");
		printSolution(solution2, start.toString(), romaniaMap);
		System.out.println("\n\n\n");
		printSolution(solution3, start.toString(), romaniaMap);
		System.out.println("\n\n\n");
		printSolution(solution4, start.toString(), romaniaMap);
		System.out.println("\n\n\n");
		printSolution(solution5, start.toString(), romaniaMap);
		System.out.println("\n\n\n");
		printSolution(solution6, start.toString(), romaniaMap);
		System.out.println("\n\n\n");
		printSolution(solution7, start.toString(), romaniaMap);
	}

}

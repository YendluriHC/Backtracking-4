import java.util.*;

public class OptimalBuildingPlacement {
    
    // Directions for 4 possible movements (up, down, left, right)
    private static final int[][] DIRECTIONS = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    
    public int findMinDistance(int width, int height, int n) {
        // Initialize the grid
        int[][] grid = new int[height][width];
        
        // List to hold all possible coordinates in the grid
        List<int[]> allCells = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                allCells.add(new int[] {i, j});
            }
        }

        // Choose n buildings using combinations
        return placeBuildings(allCells, n, height, width);
    }

    // Method to place buildings and return the minimum possible maximum distance
    private int placeBuildings(List<int[]> allCells, int n, int height, int width) {
        List<List<int[]>> combinations = new ArrayList<>();
        generateCombinations(allCells, n, 0, new ArrayList<>(), combinations);
        
        int minDistance = Integer.MAX_VALUE;
        
        // For each combination of building placements
        for (List<int[]> buildings : combinations) {
            // Run BFS to calculate maximum distance
            int[][] grid = new int[height][width];
            int maxDistance = bfs(buildings, height, width, grid);
            minDistance = Math.min(minDistance, maxDistance);
        }
        
        return minDistance;
    }

    // BFS to calculate the maximum distance from any building
    private int bfs(List<int[]> buildings, int height, int width, int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        for (int[] building : buildings) {
            queue.offer(building);
            grid[building[0]][building[1]] = 1; // Mark building cells
        }
        
        int maxDistance = 0;
        
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0], col = current[1];
            
            for (int[] dir : DIRECTIONS) {
                int newRow = row + dir[0];
                int newCol = col + dir[1];
                
                // Check if the new position is within bounds and unvisited
                if (newRow >= 0 && newRow < height && newCol >= 0 && newCol < width && grid[newRow][newCol] == 0) {
                    grid[newRow][newCol] = grid[row][col] + 1; // Distance to new cell is 1 more than current
                    maxDistance = Math.max(maxDistance, grid[newRow][newCol] - 1); // Update maximum distance
                    queue.offer(new int[] {newRow, newCol});
                }
            }
        }
        
        return maxDistance;
    }

    // Method to generate all possible combinations of building placements
    private void generateCombinations(List<int[]> allCells, int n, int start, List<int[]> current, List<List<int[]>> combinations) {
        if (current.size() == n) {
            combinations.add(new ArrayList<>(current));
            return;
        }
        
        for (int i = start; i < allCells.size(); i++) {
            current.add(allCells.get(i));
            generateCombinations(allCells, n, i + 1, current, combinations);
            current.remove(current.size() - 1);
        }
    }
    
    public static void main(String[] args) {
        OptimalBuildingPlacement solver = new OptimalBuildingPlacement();
        int result = solver.findMinDistance(4, 4, 3);
        System.out.println("Minimum possible maximum distance: " + result);  // Output should be 2
    }
}

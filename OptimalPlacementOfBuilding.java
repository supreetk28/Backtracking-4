// Time Complexity : O(h × w)
// Space Complexity : O(h × w)
import java.util.*;

public class OptimalBuildingPlacement {

    static class Cell {
        int row, col, dist;
        public Cell(int r, int c, int d) {
            row = r;
            col = c;
            dist = d;
        }
    }

    public static int minDistance(int w, int h, int n) {
        List<int[]> allPositions = new ArrayList<>();
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                allPositions.add(new int[]{i, j});

        int minMaxDist = Integer.MAX_VALUE;
        List<List<int[]>> combinations = new ArrayList<>();
        generateCombinations(allPositions, 0, n, new ArrayList<>(), combinations);

        for (List<int[]> buildings : combinations) {
            int[][] grid = new int[h][w];
            for (int[] pos : buildings) {
                grid[pos[0]][pos[1]] = 1; // Place building
            }

            int maxDist = bfs(grid, h, w);
            minMaxDist = Math.min(minMaxDist, maxDist);

            // Early exit if optimal
            if (minMaxDist == 0) break;
        }

        return minMaxDist;
    }

    private static void generateCombinations(List<int[]> positions, int start, int n,
                                             List<int[]> current, List<List<int[]>> result) {
        if (n == 0) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i <= positions.size() - n; i++) {
            current.add(positions.get(i));
            generateCombinations(positions, i + 1, n - 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    private static int bfs(int[][] grid, int h, int w) {
        Queue<Cell> queue = new LinkedList<>();
        boolean[][] visited = new boolean[h][w];
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++)
                if (grid[i][j] == 1) {
                    queue.offer(new Cell(i, j, 0));
                    visited[i][j] = true;
                }

        int maxDist = 0;

        while (!queue.isEmpty()) {
            Cell cell = queue.poll();
            maxDist = Math.max(maxDist, cell.dist);
            for (int[] dir : directions) {
                int newRow = cell.row + dir[0];
                int newCol = cell.col + dir[1];
                if (newRow >= 0 && newRow < h && newCol >= 0 && newCol < w && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    queue.offer(new Cell(newRow, newCol, cell.dist + 1));
                }
            }
        }
        return maxDist;
    }

    public static void main(String[] args) {
        int w = 4, h = 4, n = 3;
        int result = minDistance(w, h, n);
        System.out.println("Minimum max distance = " + result); // Expected: 2
    }
}

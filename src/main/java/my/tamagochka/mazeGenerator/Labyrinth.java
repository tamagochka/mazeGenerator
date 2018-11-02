package my.tamagochka.mazeGenerator;

import java.util.*;

public class Labyrinth {

    private static final int CHANCE_TO_RECURSE_SPLIT = 50;
    private static final int MINIMAL_BOX_SPLIT = 2;

    private byte[][] lab;
    private int width, height;
    private int fullWidth, fullHeight;

    private static void generateBorders(byte[][] lab) {
        for(int x = 0; x < lab[0].length; x++) {
            lab[0][x] = 1;
            lab[lab.length - 1][x] = 1;
        }
        for(int y = 0; y < lab.length; y++) {
            lab[y][0] = 1;
            lab[y][lab[0].length - 1] = 1;
        }
    }

    private static void mergeArrays(byte[][] dst_arr, int left, int top, byte[][] src_arr) {
        for(int x = 0; x < src_arr[0].length; x++)
            for(int y = 0; y < src_arr.length; y++)
                if(top + y < dst_arr.length && left + x < dst_arr[0].length)
                    dst_arr[top + y][left + x] = src_arr[y][x];
    }


    private static void boxSplit(int left, int top, int right, int bottom, int minBoxSize, int chanceToRecurseSplit, List<int[]> boxes) {

        minBoxSize = minBoxSize < 2 ? 2 : minBoxSize;
        if(chanceToRecurseSplit != 0)
            chanceToRecurseSplit = chanceToRecurseSplit / 100 == 0 ? 100 : chanceToRecurseSplit / 100;

        if(left > right) left = left + right - (right = left);
        if(top > bottom) top = top + bottom - (bottom = top);

        if(right - left <= minBoxSize * 3 || bottom - top <= minBoxSize * 3) return;

        Random random = new Random();

        // split points
        int nleft = random.nextInt(right - 3 * minBoxSize - left) + left + minBoxSize;
        int nright = random.nextInt(right - 2 * minBoxSize - nleft) + nleft + minBoxSize;
        int ntop = random.nextInt(bottom - 3 * minBoxSize - top) + top + minBoxSize;
        int nbottom = random.nextInt(bottom - 2 * minBoxSize - ntop) + ntop + minBoxSize;

        int startBoxNum = boxes.size();
        boxes.add(new int[]{nleft, ntop, nright - nleft, nbottom - ntop}); // central box

        // allocation
        byte[] parts = new byte[8];
        byte length = 0, step = 0, nextStep = 3, numParts = 1;
        while(length < 9) {
            step = (byte)(random.nextInt(nextStep) + 1);
            for(int i = 0; i < step; i++) parts[(i + length) % 8] = numParts;
            numParts++;
            length += step;
            nextStep = (byte)(3 - length % 2);
        }

        System.out.println(parts[0] + " " + parts[1] + " " + parts[2]);
        System.out.println(parts[7] + " " + " " + " " + parts[3]);
        System.out.println(parts[6] + " " + parts[5] + " " + parts[4]);


        // calculating parts coordinates
        int[][] parts_coords = new int[8][4];
        int[] coords = {left, top, nleft, ntop, nright, nbottom, right, bottom};
        int even = 0, odd = 0;
        for(int i = 0; i < 8; i++) {
            parts_coords[i][0] = coords[0 + even];
            parts_coords[i][1] = coords[1 + odd];
            parts_coords[i][2] = coords[2 + even];
            parts_coords[i][3] = coords[3 + odd];
            if((i / 2) % 2 == 0)
                even += i > 3 ? -2 : 2;
            else
                odd += i > 3 ? -2 : 2;
        }

        // connection parts
        byte[] connected_parts = new byte[8];
        byte count_connected_parts = 0;
        for(int i = 0; i < 8; i++) {
            boolean connected = false;
            for(int j = 0; j < count_connected_parts; j++)
                if(parts[i] == connected_parts[j]) connected = true;
            if(connected) continue;
            int[] block_coords = {parts_coords[i][0], parts_coords[i][1], parts_coords[i][2], parts_coords[i][3]};
            for(int j = 0; j < 8; j++) {
                if(parts[i] == parts[j]) {
                    for(int k = 0; k < 4; k++)
                        if(k < 2)
                            block_coords[k] = Math.min(block_coords[k], parts_coords[j][k]);
                        else
                            block_coords[k] = Math.max(block_coords[k], parts_coords[j][k]);
                }
            }

            boxes.add(new int[]{block_coords[0], block_coords[1], block_coords[2] - block_coords[0], block_coords[3] - block_coords[1]});
            connected_parts[count_connected_parts] = parts[i];
            count_connected_parts++;
        }

        int endBoxNum = boxes.size();

        for(int i = startBoxNum; i < endBoxNum; i++) {
            if(random.nextInt(100) < chanceToRecurseSplit)
                boxSplit(boxes.get(i)[0], boxes.get(i)[1],
                        boxes.get(i)[0] + boxes.get(i)[2], boxes.get(i)[1] + boxes.get(i)[3],
                        minBoxSize, chanceToRecurseSplit, boxes);
        }
    }

    private static byte[][] generator(int width, int height, Position start, Position finish) {

        int fullWidth = width * 2 + 1; // full size with borders and walls
        int fullHeight = height * 2 + 1;

        Position fullStart = null;
        Position fullFinish = null;

        if(start != null) fullStart = new Position(start.getX() * 2 + 1, start.getY() * 2 + 1);
        else fullStart = new Position(1, 1);
        if(finish != null) fullFinish = new Position(finish.getX() * 2 + 1, finish.getY() * 2 + 1);
        else fullFinish = new Position((width - 1) * 2 + 1, (height - 1) * 2 + 1);

        byte[][] lab = new byte[fullHeight][fullWidth];

        generateBorders(lab);

        // generate earth
        for(int x = 1; x < fullWidth - 1; x++)
            for(int y = 1; y < fullHeight - 1; y++) {
                lab[y][x] = 2;
            }

        // generate columns
        for(int x = 2; x < fullWidth - 2; x += 2)
            for(int y = 2; y < fullHeight - 2; y += 2) {
                lab[y][x] = 1;
            }

        lab[fullStart.getY()][fullStart.getX()] = 0;
        lab[0][0] = 2;
        lab[fullHeight - 1][0] = 2;
        lab[0][fullWidth - 1] = 2;
        lab[fullHeight - 1][fullWidth - 1] = 2;

        // generation
        Random random = new Random();
        Position currentPos = new Position(fullStart);
        Position newPos = null;
        boolean endGeneration = false;
        byte dir = -1;
        int step = 1;

        while(!endGeneration) {
            newPos = null;
            if(currentPos.getX() % 2 != 0 && currentPos.getY() % 2 != 0)
                dir = (byte)random.nextInt(4);
            switch(dir) {
                case 0: // up
                    newPos = currentPos.subY(step);
                    break;
                case 1: // down
                    newPos = currentPos.addY(step);
                    break;
                case 2: // left
                    newPos = currentPos.subX(step);
                    break;
                case 3: // right
                    newPos = currentPos.addX(step);
                    break;
                default:
                    continue;
            }

            if(lab[newPos.getY()][newPos.getX()] == 1) continue; // column collision
            if(lab[newPos.getY()][newPos.getX()] == 0) currentPos = newPos; // empty space
            if(currentPos.equals(fullFinish) && lab[newPos.getY()][newPos.getX()] == 2) continue; // not dig from finish
            if(lab[newPos.getY()][newPos.getX()] == 2) { // can dig
                int dx = newPos.getX() - currentPos.getX();
                int dy = newPos.getY() - currentPos.getY();
                byte sum = (byte)(lab[newPos.addY(dx).getY()][newPos.addX(dy).getX()] +
                        lab[newPos.subY(dx).getY()][newPos.subX(dy).getX()] +
                        lab[newPos.addY(dy).getY()][newPos.addX(dx).getX()] +
                        lab[newPos.addY(dx).addY(dy).getY()][newPos.addX(dy).addX(dx).getX()] +
                        lab[newPos.subY(dx).addY(dy).getY()][newPos.subX(dy).addX(dx).getX()]
                );
                if(sum >= 7) {
                    lab[newPos.getY()][newPos.getX()] = 0;
                    currentPos = newPos;
                }
            }
            // check maze ready
            endGeneration = true;
            for(int x = 0; x < fullWidth - 1; x++) {
                for(int y = 0; y < fullHeight - 1; y++) {
                    if(lab[y][x] != 0 && lab[y + 1][x] != 0 && lab[y][x + 1] != 0 && lab[y + 1][x + 1] != 0) {
                        endGeneration = false;
                        break;
                    }
                }
                if(!endGeneration) break;
            }
        }

        for(int x = 0; x < fullWidth; x++)
            for(int y = 0; y < fullHeight; y++)
                if(lab[y][x] != 0) lab[y][x] = 1;

        if(start != null)
            lab[fullStart.getY()][fullStart.getX()] = 5; // start point
        if(finish != null)
            lab[fullFinish.getY()][fullFinish.getX()] = 7; // finish point

        return lab;
    }

    public static Labyrinth simpleGenerator(int width, int height, Position start, Position finish) {
        byte[][] lab = generator(width, height, start, finish);
        Labyrinth labyrinth = new Labyrinth(width, height, lab[0].length, lab.length, lab);
        return labyrinth;
    }

    private static int findContactPoint(int first_side_pos, int second_side_pos,
                                        int first_side_start, int first_side_end,
                                        int second_side_start, int second_side_end) {
        if(first_side_pos == second_side_pos) {
            if(first_side_start < second_side_end &&
                    first_side_end > second_side_start) {
                int s_touch = Math.max(first_side_start, second_side_start);
                int e_touch = Math.min(first_side_end, second_side_end);
                Random random = new Random();
                return random.nextInt(e_touch - s_touch) + s_touch;
            }
        }
        return -1;
    }

    private static int[][] pathsFinder(int startPoint, int targetPoint, int[][] graph) {
        List<int[]> paths = new LinkedList<>();
        pathsFinder(startPoint, targetPoint, graph, new int[graph.length + 1], 0, new boolean[graph.length], paths);
        return paths.toArray(new int[][]{});
    }

    private static void pathsFinder(int startPoint, int targetPoint, int[][] graph, int[] path, int count, boolean[] visited, List<int[]> paths) {
        path[count + 1] = startPoint;
        count++;
        if(startPoint == targetPoint) {
            path[0] = count;
            int[] copy = new int[path.length];
            System.arraycopy(path, 0, copy, 0, path.length);
            paths.add(copy);
            count = 0;
            return;
        }
        visited[startPoint] = true;
        for(int i = 0; i < graph.length; i++) {
            if(graph[startPoint][i] != 0 && !visited[i])
                pathsFinder(i, targetPoint, graph, path, count, visited, paths);
        }
        visited[startPoint] = false;
    }

    public static Labyrinth clusterGenerator(int width, int height, Position start, Position finish) {

        if(width < MINIMAL_BOX_SPLIT || height < MINIMAL_BOX_SPLIT) return null;

        int fullWidth = width * 2 + 1; // full size with borders and walls
        int fullHeight = height * 2 + 1;

        byte[][] lab = new byte[fullHeight][fullWidth];

        List<int[]> pieces = new LinkedList<>();
//        boxSplit(0, 0, width, height, MINIMAL_BOX_SPLIT, CHANCE_TO_RECURSE_SPLIT, pieces);
        // pieces [0] - x, [1] - y, [2] - w, [3] - h

        pieces.add(new int[]{3, 2, 3, 3});
        pieces.add(new int[]{0, 0, 6, 2});
        pieces.add(new int[]{6, 0, 3, 2});
        pieces.add(new int[]{6, 2, 3, 3});
        pieces.add(new int[]{0, 5, 9, 4});
        pieces.add(new int[]{0, 2, 3, 3});


        // move start piece to up list, and finish to down list
        int f = 0, e = 0;
        for(int i = 0; i < pieces.size(); i++) {
            if(pieces.get(i)[0] <= start.getX() && start.getX() < pieces.get(i)[0] + pieces.get(i)[2] &&
                pieces.get(i)[1] <= start.getY() && start.getY() < pieces.get(i)[1] + pieces.get(i)[3]) {
                f = i;
            }
        }
        pieces.add(0, pieces.remove(f));
        for(int i = 0; i < pieces.size(); i++) {
            if(pieces.get(i)[0] <= finish.getX() && finish.getX() < pieces.get(i)[0] + pieces.get(i)[2] &&
                    pieces.get(i)[1] <= finish.getY() && finish.getY() < pieces.get(i)[1] + pieces.get(i)[3]) {
                e = i;
            }
        }
        pieces.add(pieces.remove(e));

        // find connection point for pieces and build adjacency matrix
        Position[][] adj = new Position[pieces.size()][pieces.size()]; // adjacency matrix
        for(int i = 0; i < pieces.size(); i++) {
            for(int j = 0; j < pieces.size(); j++) {
                int point = findContactPoint(pieces.get(i)[0] + pieces.get(i)[2], pieces.get(j)[0],
                        pieces.get(i)[1], pieces.get(i)[1] + pieces.get(i)[3],
                        pieces.get(j)[1], pieces.get(j)[1] + pieces.get(j)[3]);
                if(point >= 0) {
                    adj[i][j] = new Position(pieces.get(j)[0] - (i > j ? 1 : 0), point);
                    adj[j][i] = new Position(pieces.get(j)[0] - (i > j ? 0 : 1), point);
                }
                point = findContactPoint(pieces.get(i)[1] + pieces.get(i)[3], pieces.get(j)[1],
                        pieces.get(i)[0], pieces.get(i)[0] + pieces.get(i)[2],
                        pieces.get(j)[0], pieces.get(j)[0] + pieces.get(j)[2]);
                if(point >= 0) {
                    adj[i][j] = new Position(point, pieces.get(j)[1] - (i > j ? 1 : 0));
                    adj[j][i] = new Position(point, pieces.get(j)[1] - (i > j ? 0 : 1));
                }
            }
        }

        // build all possible paths
        int[][] graph = new int[pieces.size()][pieces.size()];
        for(int i = 0; i < pieces.size(); i++) {
            for(int j = 0; j < pieces.size(); j++) {
                if(adj[i][j] != null)
                    graph[i][j] = 1;
            }
        }
        // structure paths == [length route][r][o][u][t][e]...
        int[][] paths = pathsFinder(0, pieces.size() - 1, graph);
        Arrays.sort(paths, Comparator.comparingInt(i -> i[0]));

        // find most frequent path length
        int maxFreqLen = 0;
        int countMaxFreqLen = 0;
        for(int i = 0; i < paths.length; i++) {
            int countFreqLen = 0;
            for(int j = 0; j < paths.length; j++) {
                if(paths[i][0] == paths[j][0]) {
                    countFreqLen++;
                }
            }
            if(countMaxFreqLen < countFreqLen) {
                countMaxFreqLen = countFreqLen;
                maxFreqLen = paths[i][0];
            }
        }

        // select the main path from start to end
        int mainPath = 0;
        Random random = new Random();
        for(int i = 0; i < paths.length; i++) {
            if(paths[i][0] == maxFreqLen) {
                mainPath = i + random.nextInt(countMaxFreqLen);
                break;
            }
        }

        System.out.println("main path: ");
        for(int i = 1; i < paths[mainPath][0] + 1; i++) {
            System.out.print(paths[mainPath][i] + " ");
        }
        System.out.println();
        for(int i = 0; i < paths.length; i++) {
            for(int j = 0; j < paths[i][0] + 1; j++) {
                System.out.print(paths[i][j] + " ");
            }
            System.out.println();
        }







        // generate and merge all pieces to one big maze
        mergeArrays(lab, pieces.get(0)[0] * 2, pieces.get(0)[1] * 2, // generate start maze
                generator(pieces.get(0)[2], pieces.get(0)[3], new Position(start.getX() - pieces.get(0)[0], start.getY() - pieces.get(0)[1]), null));
        for(int i = 1; i < pieces.size() - 1; i++) {
            byte[][] pieceLab = generator(pieces.get(i)[2], pieces.get(i)[3], null, null);
            mergeArrays(lab, pieces.get(i)[0] * 2, pieces.get(i)[1] * 2, pieceLab);
        }
        mergeArrays(lab, pieces.get(pieces.size() - 1)[0] * 2, pieces.get(pieces.size() - 1)[1] * 2, // generate finish maze
                generator(pieces.get(pieces.size() - 1)[2], pieces.get(pieces.size() - 1)[3], null, new Position(finish.getX() - pieces.get(pieces.size() - 1)[0], finish.getY() - pieces.get(pieces.size() - 1)[1])));

        // put connect point
        for(int i = 0; i < adj.length; i++) {
            for(int j = 0; j < adj.length; j++) {
                if(adj[i][j] != null)
                    lab[adj[i][j].getY() * 2 + 1][adj[i][j].getX() * 2 + 1] = (byte)(i < j ? 7 : 5);
            }
        }
        // put spaces on connect point
        for(int i = 0; i < adj.length; i++) {
            for(int j = i + 1; j < adj.length; j++) {
                if(adj[i][j] != null) {
                    Position endPoint = new Position(adj[i][j].getX() * 2 + 1, adj[i][j].getY() * 2 + 1);
                    Position startPoint = new Position(adj[j][i].getX() * 2 + 1, adj[j][i].getY() * 2 + 1);
                    Position diff = endPoint.diff(startPoint);
                    Position space = startPoint.sum(diff.normalize());
                    lab[space.getY()][space.getX()] = 0;
                }
            }
        }



        Labyrinth labyrinth = new Labyrinth(width, height, fullWidth, fullHeight, lab);
        return labyrinth;
    }

    private Labyrinth(int width, int height, int fullWidth, int fullHeight, byte[][] lab) {
        this.width = width;
        this.height = height;
        this.fullWidth = fullWidth;
        this.fullHeight = fullHeight;
        this.lab = lab;
    }

    public int getFullWidth() {
        return fullWidth;
    }

    public int getFullHeight() {
        return fullHeight;
    }

    public byte getPoint(Position position) {
        return lab[position.getY()][position.getX()];
    }

}

package my.tamagochka.matrix;

public class Matrix {

    public static int[][] getIdentity(int length) {
        int[][] C = new int[length][length];
        for(int i = 0; i < length; i++) {
            C[i][i] = 1;
        }
        return C;
    }

    public static boolean isMatched(int[][] A, int[][] B) {
        return A[0].length == B.length;
    }

    public static int[][] mul(int[][] A, int[][] B) {
        if(!isMatched(A, B)) return null;
        int[][] C = new int[A.length][B[0].length];
        for(int i = 0; i < A.length; i++) {
            for(int j = 0; j < B[0].length; j++) {
                for(int k = 0; k < B.length; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static int[][] pow(int[][] A, int pow) {
        int[][] C = getIdentity(A.length);
        for(int i = 0; i < pow; i++) {
            C = mul(C, A);
        }
        return C;
    }

}

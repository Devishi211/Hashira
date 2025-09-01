import java.io.*;
import java.util.*;

public class hashira_devishi {

    // Convert char → digit value
    static int charToVal(char c) {
        if (c >= '0' && c <= '9') return c - '0';
        if (c >= 'a' && c <= 'z') return 10 + (c - 'a');
        if (c >= 'A' && c <= 'Z') return 10 + (c - 'A');
        return -1;
    }

    // Convert base string → decimal
    static long convertToDecimal(String str, int base) {
        long result = 0;
        for (char c : str.toCharArray()) {
            int val = charToVal(c);
            if (val < 0 || val >= base) {
                throw new RuntimeException("Invalid digit " + c + " for base " + base);
            }
            result = result * base + val;
        }
        return result;
    }

    // Construct polynomial coefficients from roots
    static long[] constructPolynomial(long[] roots, int k) {
        long[] coeffs = new long[k + 1];
        coeffs[0] = 1; // polynomial starts as 1

        for (int i = 0; i < k; i++) {
            long root = roots[i];
            for (int j = i + 1; j >= 1; j--) {
                coeffs[j] = coeffs[j] - root * coeffs[j - 1];
            }
        }
        return coeffs;
    }

    public static void main(String[] args) throws Exception {
        // ✅ Read from input.json file
        BufferedReader br = new BufferedReader(new FileReader("input.json"));
        String line;
        int n = 0, k = 0;

        // --- Step 1: Read n and k ---
        while ((line = br.readLine()) != null) {
            if (line.contains("\"n\"")) {
                n = Integer.parseInt(line.replaceAll("[^0-9]", ""));
            } else if (line.contains("\"k\"")) {
                k = Integer.parseInt(line.replaceAll("[^0-9]", ""));
                break;
            }
        }

        long[] roots = new long[k];
        int count = 0;

        // --- Step 2: Read base + value ---
        while ((line = br.readLine()) != null) {
            if (line.contains("base")) {
                String numOnly = line.replaceAll("[^0-9]", "");
                int base = Integer.parseInt(numOnly);

                String valueLine = br.readLine();
                String valueStr = valueLine.replaceAll("[^0-9a-zA-Z]", "");

                long decVal = convertToDecimal(valueStr, base);
                if (count < k) {
                    roots[count++] = decVal;
                }
            }
        }

        // --- Step 3: Construct polynomial ---
        long[] coeffs = constructPolynomial(roots, k);

        // --- Step 4: Print polynomial coefficients ---
        System.out.println("Polynomial coefficients (from x^" + k + " down to constant):");
        for (int i = 0; i <= k; i++) {
            System.out.print(coeffs[i] + " ");
        }
        System.out.println();
    }
}

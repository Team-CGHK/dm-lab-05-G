import java.math.BigInteger;
import java.io.*;

public class Template {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws IOException  {
		BufferedReader br = new BufferedReader(new FileReader("num2brackets2.in"));
		String[] parts = br.readLine().split(" ");
		int n = Integer.parseInt(parts[0]);
		BigInteger k = (new BigInteger(parts[1]));
		k = k.add(BigInteger.ONE);

		BigInteger d[][] = new BigInteger[n * 2 + 1][n + 1];
		for (int i = 0; i <= n * 2; ++i)
			for (int j = 0; j <= n; ++j)
				d[i][j] = BigInteger.ZERO;
		d[0][0] = BigInteger.ONE;
		for (int i = 0; i < n * 2; ++i)
			for (int j = 0; j <= n; ++j) {
				if (j + 1 <= n)
					d[i + 1][j + 1] = d[i + 1][j + 1].add(d[i][j]);
				if (j > 0)
					d[i + 1][j - 1] = d[i + 1][j - 1].add(d[i][j]);
			}

		String ans = new String();
		int depth = 0;
		char[] stack = new char[n * 2];
		int stacksz = 0;
		for (int i = n * 2 - 1; i >= 0; --i) {
			BigInteger cur;
			if (depth + 1 <= n)
				cur = d[i][depth + 1].shiftLeft((i - depth - 1) / 2);
			else
				cur = BigInteger.ZERO;
			if (cur.compareTo(k) >= 0) {
				ans += '(';
				stack[stacksz++] = '(';
				++depth;
				continue;
			}
			k = k.subtract(cur);
			if (stacksz > 0 && stack[stacksz - 1] == '(' && depth - 1 >= 0)
				cur = d[i][depth - 1].shiftLeft((i - depth + 1) / 2);
			else
				cur = BigInteger.ZERO;
			if (cur.compareTo(k) >= 0) {
				ans += ')';
				--stacksz;
				--depth;
				continue;
			}
			k = k.subtract(cur);
			if (depth + 1 <= n)
				cur = d[i][depth + 1].shiftLeft((i - depth - 1) / 2);
			else
				cur = BigInteger.ZERO;
			if (cur.compareTo(k) >= 0) {
				ans += '[';
				stack[stacksz++] = '[';
				++depth;
				continue;
			}
			k = k.subtract(cur);
			ans += ']';
			--stacksz;
			--depth;
		}
		BufferedWriter bw = new BufferedWriter(new PrintWriter("num2brackets2.out"));
		bw.write(ans);
		bw.close();
	}
}

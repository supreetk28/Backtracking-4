// Time Complexity : O(n^2 + m * k)
// Space Complexity : O(m * k)
mport java.util.*;

public class BraceExpansion {

    public List<String> expand(String expression) {
        List<String> result = helper(expression);
        Collections.sort(result);
        return result;
    }

    private List<String> helper(String expr) {
        List<String> result = new ArrayList<>();
        Stack<List<String>> stack = new Stack<>();
        int i = 0;
        while (i < expr.length()) {
            char ch = expr.charAt(i);
            if (ch == '{') {
                int j = i, count = 0;
                while (i < expr.length()) {
                    if (expr.charAt(i) == '{') count++;
                    else if (expr.charAt(i) == '}') count--;
                    if (count == 0) break;
                    i++;
                }
                String subExpr = expr.substring(j + 1, i);
                List<String> options = new ArrayList<>();
                for (String part : splitComma(subExpr)) {
                    options.addAll(helper(part));
                }
                stack.push(options);
                i++;
            } else if (Character.isLetter(ch)) {
                List<String> single = new ArrayList<>();
                single.add(String.valueOf(ch));
                stack.push(single);
                i++;
            } else {
                i++;
            }
        }

        return cartesian(stack);
    }

    private List<String> splitComma(String s) {
        List<String> result = new ArrayList<>();
        int brace = 0;
        StringBuilder sb = new StringBuilder();
        for (char ch : s.toCharArray()) {
            if (ch == ',' && brace == 0) {
                result.add(sb.toString());
                sb.setLength(0);
            } else {
                if (ch == '{') brace++;
                if (ch == '}') brace--;
                sb.append(ch);
            }
        }
        result.add(sb.toString());
        return result;
    }

    private List<String> cartesian(Stack<List<String>> stack) {
        if (stack.isEmpty()) return new ArrayList<>();

        Stack<List<String>> reverse = new Stack<>();
        while (!stack.isEmpty()) reverse.push(stack.pop());

        List<String> res = reverse.pop();

        while (!reverse.isEmpty()) {
            List<String> temp = new ArrayList<>();
            for (String a : res) {
                for (String b : reverse.peek()) {
                    temp.add(a + b);
                }
            }
            res = temp;
            reverse.pop();
        }

        return res;
    }

    public static void main(String[] args) {
        BraceExpansion be = new BraceExpansion();
        System.out.println(be.expand("{a,b}{c,{d,e}}")); // [ac, ad, ae, bc, bd, be]
    }
}

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
//优先级
public class Operation {
    private static int ADDITION=1;
    private static int SUBTRACTION=1;
    private static int MULTIPLICATION=2;
    private static int DIVISION=2;

    public static int getValue(String operation){
        int result;
        switch (operation){
            case "+":
                result=ADDITION;
                break;
            case "-":
                result=SUBTRACTION;
                break;
            case "*":
                result=MULTIPLICATION;
                break;
            case "/":
                result=DIVISION;
                break;
            default:
                result=0;
        }
        return result;
    }
}



class PolishNotation {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入运算表达式:");
        String expressionStr=sc.nextLine();
        List<String> zx= toInfixExpression(expressionStr);
        List<String> rpn=parseSuffixExpression(zx);

        System.out.println("计算结果:"+ calculate(rpn));
    }


     // 把字符串转换成中序表达式

    public static List<String> toInfixExpression(String s) {
        List<String> ls = new ArrayList<String>();//存储中序表达式
        int i = 0;
        String str;
        char c;
        do {
            if ((c = s.charAt(i)) < 48&&(c = s.charAt(i))!=46|| (c = s.charAt(i)) > 57) {
                ls.add("" + c);
                i++;
            } else {
                str = "";
                while (i < s.length() && (c = s.charAt(i)) >= 46&&(c = s.charAt(i))!=47
                        && (c = s.charAt(i)) <= 57){
                    str += c;
                    i++;
                }
                ls.add(str);
            }

        } while (i < s.length());
        return ls;
    }

   //转换成逆波兰表达式

    public static List<String> parseSuffixExpression(List<String> ls) {
        Stack<String> s1=new Stack<String>();
        List<String> lss = new ArrayList<String>();
        for (String ss : ls) {
            if (ss.matches("\\d+||(\\d+\\.\\d+)")) {
                lss.add(ss);
            } else if (ss.equals("(")) {
                s1.push(ss);
            } else if (ss.equals(")")) {

                while (!s1.peek().equals("(")) {
                    lss.add(s1.pop());
                }
                s1.pop();
            } else {
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(ss)) {
                    lss.add(s1.pop());
                }
                s1.push(ss);
            }
        }
        while (s1.size() != 0) {
            lss.add(s1.pop());
        }
        return lss;
    }

//通过逆波兰表达式计算结果

    public static double calculate(List<String> ls) {
        Stack<String> s=new Stack<String>();
        for (String str : ls) {
            if (str.matches("\\d+||(\\d+\\.\\d+)")) {
                s.push(str);
            } else {
                double b = Double.parseDouble(s.pop());
                double a = Double.parseDouble(s.pop());
                double result=0D;
                if (str.equals("+")) {
                    result = a + b;
                } else if (str.equals("-")) {
                    result = a - b;
                } else if (str.equals("*")) {
                    result = a * b;
                } else if (str.equals("/")) {
                    try{
                        result = a / b;
                        int error;
                        if(b==0D)
                        error=1/0;
                    }catch(Exception e){
                        System.out.println("错误，0不能作为除数");
                        System.exit(0);
                    }


                }
                s.push("" + result);
            }
        }
        return Double.parseDouble(s.pop());
    }
}

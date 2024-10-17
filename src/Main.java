import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }
            if (!input.contains("+") && !input.contains("-") && !input.contains("*") && !input.contains("/")) {
                throw new IllegalArgumentException("Произведена неподходящая операция");
            }
            try {

                String res = calculate(input);
                System.out.println(res);
            } catch (Exception e) {

                System.out.println("ошибка!!!" + e.getMessage());
                break;
            }
        }
        sc.close();
    }

    public static String calculate(String expression) throws Exception {
        expression = expression.trim();


        if (expression.contains("+")) {
            return plus(expression);
        } else if (expression.contains("-")) {
            return minus(expression);
        } else if (expression.contains("*")) {
            return multi(expression);
        } else if (expression.contains("/")) {
            return Division(expression);
        }
        return expression;
    }

    private static String plus(String expression) throws Exception {
        Exclusion(expression);

        String[] parts = expression.split(" \\+ ");
        return limitLength("\"" + parts[0].replace("\"", "").trim() + parts[1].replace("\"", "").trim() + "\"");
    }

    private static String minus(String expression) throws Exception {
        String[] parts = expression.split(" - ");

        String str1 = parts[0].replace("\"", "");
        String str2 = parts[1].replace("\"", "");

        String result = str1.replace(str2, "").replaceAll(" ", " ");
        return limitLength("\"" + result + "\""); //
    }

    private static String multi(String expression) throws Exception {
        Exclusion(expression);

        String[] parts = expression.split(" ");

        if (parts.length != 3) {
            throw new Exception("Ошибка: неверное количество аргументов");
        }

        String str1 = parts[0];
        String operator = parts[1];
        String str2 = parts[2];

        if (operator.equals("*")) {
            try {
                int multi = Integer.parseInt(str2);
                return limitLength("\"" + str1.repeat(multi).replace("\"", "") + "\"");
            } catch (NumberFormatException e) {
                return "Ошибка: второй аргумент не является целым числом";
            }
        }
        return "Ошибка: неизвестный оператор";
    }

    private static String Division(String expression) throws Exception {
        Exclusion(expression);
        String[] parts = expression.split(" ");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Ошибка: Неверный формат ввода. Оживается 'строка оператор строка'.");
        }

        String str1 = parts[0];
        String operator = parts[1];
        String str2 = parts[2];

        if (operator.equals("/")) {

            int divisor = Integer.parseInt(str2.trim());
            if (divisor == 0) {
                throw new ArithmeticException("Ошибка: Деление на ноль");
            }

            String result = str1.substring(0, Math.max(0, str1.length() / divisor));

            return limitLength("\"" + result.replace("\"", "") + "\"");
        }

        return "Неизвестный оператор";
    }

    private static String Exclusion(String expression) throws Exception {
        String[] parts = expression.split(" ");

        if (parts.length < 3) {
            throw new Exception("Некорректное выражение. Ожидалось: \"строка\" операция \"строка/число\"");
        }

        String str1 = parts[0].trim();
        String operator = parts[1].trim();
        String str2 = parts[2].trim();

        if (!(str1.startsWith("\"") && str1.endsWith("\""))) {
            throw new Exception("Первая строка должна начинаться и заканчиваться в кавычках");
        }

        String longStr1 = str1.replace("\"", "").trim();
        if (longStr1.length() > 10) {
            throw new Exception("Ошибка (Первая строка не должна превышать 10 символов!)");
        }

        if (operator.equals("*") || operator.equals("/")) {
            if (str2.startsWith("\"") && str2.endsWith("\"")) {
                throw new Exception("Ошибка (Для операции " + operator + " второе значение должно быть числом)");
            } else {
                try {
                    int numericalStr2 = Integer.parseInt(str2);
                    if (numericalStr2 < 1 || numericalStr2 > 10) {
                        throw new Exception("Ошибка (Число должно быть от 1 до 10 включительно)");
                    }
                } catch (NumberFormatException e) {
                    throw new Exception("Ошибка (Для операции " + operator + " второе значение должно быть числом/ целым числом)");
                }
            }
        } else {

            if (!(str2.startsWith("\"") && str2.endsWith("\""))) {
                throw new Exception("Ошибка (Вторая строка должна начинаться и заканчиваться в кавычках)");
            }
            String processedStr2 = str2.replace("\"", "").trim();
            if (processedStr2.length() > 10) {
                throw new Exception("Ошибка (Вторая строка не должна превышать 10 символов!)");
            }
        }
        return "неизвестная ошибка";
    }

    public static String limitLength(String expression) {
        return expression.length() > 40 ? expression.substring(0, 40) + "..." : expression;
    }
}
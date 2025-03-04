package cal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArithmeticCalculator<T extends Number> {

    private final List<Double> results = new ArrayList<>();
    private final Scanner sc = new Scanner(System.in);

    // 결과 리스트 반환
    public List<Double> getResults() {
        return new ArrayList<>(results); // 원본 보호를 위해 복사본 반환
    }

    // 결과 리스트 설정
    public void setResults(List<Double> newResults) {
        results.clear();
        results.addAll(newResults);
    }

    // 첫 번째 숫자 입력
    public String FirstInput() {
        while (true) {
            System.out.println("첫 번째 숫자를 입력하세요 ('exit' 입력 시 종료, 'remove' 입력 시 첫 번째 결과 삭제): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("remove")) return input;

            if (isValidNumber(input)) return input;

            System.out.println("올바른 숫자를 입력하세요.");
        }
    }

    // 두 번째 숫자 입력
    public String SecondInput() {
        while (true) {
            System.out.println("두 번째 숫자를 입력하세요 ('exit' 입력 시 종료): ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) return input;

            if (isValidNumber(input)) return input;

            System.out.println("올바른 숫자를 입력하세요.");
        }
    }

    // 연산자 입력
    public char OperatorInput() {
        while (true) {
            System.out.println("연산자를 입력하세요 (+,-,*,/) 또는 'exit' 입력 시 종료: ");
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) return 'e'; // 종료 표시

            if (isValidOperator(input)) return input.charAt(0);

            System.out.println("올바른 연산자를 입력하세요.");
        }
    }

    // 추가 기능 입력
    public String AdditionalInput() {
        while (true) {
            System.out.println("결과 목록에서 큰 값을 보려면 숫자를 입력하세요.");
            System.out.println("결과값의 합계를 보려면 'sum'을 입력하세요.");
            System.out.println("초기 화면으로 돌아가려면 Enter를 누르세요.");
            System.out.print("입력: ");

            String input = sc.nextLine().trim();

            if (input.isBlank() || input.equalsIgnoreCase("sum") || isValidNumber(input)) return input;

            System.out.println("올바른 숫자 또는 'sum'을 입력하세요.");
        }
    }

    // 숫자 유효성 검사
    public boolean isValidNumber(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // 연산자 유효성 검사
    public boolean isValidOperator(String input) {
        return input.length() == 1 && "+-*/".contains(input);
    }

    public void processAdditionalFunctions(String input) {
        if (input.equalsIgnoreCase("sum")) {
            sumRange();
            return;
        }

        try {
            double filterNum = Double.parseDouble(input);
            printBiggerResultsWithForEach(filterNum);
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하지 않았습니다.");
        }
    }

    public double calculate(T num1, char operator, T num2) {
        try {
            Operation operation = Operation.fromSymbol(String.valueOf(operator));
            double result = operation.apply(num1, num2);
            results.add(result);
            return result;
        } catch (IllegalArgumentException e) {
            System.out.println("연산 중 오류 발생: " + e.getMessage());
            return Double.NaN;
        }
    }

    public void sumRange() {
        int start, end;

        while (true) {
            System.out.println("시작값을 입력하세요: ");
            String startInput = sc.nextLine().trim();
            if (isValidNumber(startInput)) {
                start = Integer.parseInt(startInput);
                break;
            }
            System.out.println("올바른 숫자를 입력하세요.");
        }

        while (true) {
            System.out.println("끝값을 입력하세요: ");
            String endInput = sc.nextLine().trim();
            if (isValidNumber(endInput)) {
                end = Integer.parseInt(endInput);
                break;
            }
            System.out.println("올바른 숫자를 입력하세요.");
        }

        if (start > end) {
            System.out.println("시작 인덱스는 끝 인덱스보다 작거나 같아야 합니다.");
            return;
        }

        double sum = sumResults(start, end);
        System.out.printf("입력한 범위: [%d, %d]%n해당 범위 내의 결과 값 합계: %.2f%n", start, end, sum);
    }

    public double sumResults(int start, int end) {
        try {
            return results.subList(start - 1, end).stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("유효하지 않은 인덱스 범위입니다. 범위를 확인하세요.");
            return 0.0;
        }
    }
    // 결과 리스트에서 첫 번째 값 삭제
    public void removeList() {
        if (!results.isEmpty()) {
            results.remove(0);
            System.out.println("첫 번째 결과값이 삭제되었습니다.");
        } else {
            System.out.println("삭제할 결과값이 없습니다.");
        }
    }

    public void printBiggerResultsWithForEach(double num) {
        results.stream()
                .filter(result -> result > num)
                .forEach(result -> System.out.println("필터링된 값: " + result));
    }

    // 연산을 위한 내부 enum 클래스
    public enum Operation {
        ADD("+", (a, b) -> a.doubleValue() + b.doubleValue()),
        SUBTRACT("-", (a, b) -> a.doubleValue() - b.doubleValue()),
        MULTIPLY("*", (a, b) -> a.doubleValue() * b.doubleValue()),
        DIVIDE("/", (a, b) -> {
            if (b.doubleValue() == 0) throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
            return a.doubleValue() / b.doubleValue();
        });

        private final String symbol;
        private final Calculator calculator;

        Operation(String symbol, Calculator calculator) {
            this.symbol = symbol;
            this.calculator = calculator;
        }

        public double apply(Number a, Number b) {
            return calculator.calculate(a, b);
        }

        public static Operation fromSymbol(String symbol) {
            for (Operation op : values()) {
                if (op.symbol.equals(symbol)) {
                    return op;
                }
            }
            throw new IllegalArgumentException("유효하지 않은 연산자입니다. (+, -, *, / 만 허용)");
        }

        @FunctionalInterface
        interface Calculator {
            double calculate(Number a, Number b);
        }
    }
}

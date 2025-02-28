package cal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArithmeticCalculator<T extends Number> {

    // 속성
    private final List<Double> results;
    private String firstInput;
    private String secondInput;
    private char operator;
    private String thirdInput;


    // 생성자
    public ArithmeticCalculator() {
        results = new ArrayList<>();
    }


    //  입력값 검증 메서드
    public boolean validateInput(String input) {
        if (input.trim().isEmpty()) { // 공백 입력 확인
            System.out.println("아무 값도 입력하지 않았습니다. 값을 다시 입력하세요.");
            return false;
        }
        return true;
    }

    // 종료 처리 메서드
    public static boolean exit(String input) {
        return input.equalsIgnoreCase("exit");
    }

    // 현재 결과 리스트에서 입력값보다 더 큰 결과를 출력시키는 메서드
    public static boolean bigger(String input) {
        return input.equalsIgnoreCase("bigger");
    }
    // 지정 범위 결과 값 합계 출력 시키는 메서드
    public static boolean sum(String input) {
        return input.equalsIgnoreCase("sum");
    }


    // 첫 번째 숫자 입력 메서드
    public void setFirstInput(Scanner sc) {
        while (true) {
            System.out.println("첫 번째 숫자를 입력하세요. 'exit' 입력 시 종료, 'remove' 입력시 첫번 째 결과 값 삭제 : ");
            String input = sc.nextLine();

            // 입력 유효성 검사
            if (!validateInput(input)) {
                continue;
            }

            // 종료 처리
            if (exit(input)) {
                firstInput = "exit"; // 종료
                return;
            }



            // remove 처리
            if (input.equalsIgnoreCase("remove")) {
                removeList();
                System.out.println("삭제 후 현재 값들 : " + getResults());
                continue; // remove 처리 후 다시 입력
            }

            firstInput = input; // 유효한 입력을 설정
            return;
        }
    }

    // 첫 번째 입력값 반환
    public String getFirstInput() {
        return firstInput;
    }
    // 연산자 입력 메서드
    public void setOperator(Scanner sc) {
        while (true) {
            System.out.println("연산자를 입력하세요 (+,-,*,/) 또는 'exit' 입력 시 종료:");
            String input = sc.nextLine();

            // 입력 유효성 검사
            if (!validateInput(input)) {
                continue;
            }

            // 종료 처리
            if (exit(input)) {
                operator = 'e'; // 종료를 알리는 특수 문자
                return;
            }

            operator = input.charAt(0); // 연산자 설정
            return;
        }
    }
    // 연산자 반환
    public char getOperator() {
        return operator;
    }


    // 두 번째 숫자 입력 메서드
    public void setSecondInput(Scanner sc) {
        while (true) {
            System.out.println("두 번째 숫자를 입력하세요 또는 'exit' 입력 시 종료:");
            String input = sc.nextLine();

            // 입력 유효성 검사
            if (!validateInput(input)) {
                continue;
            }

            // 종료 처리
            if (exit(input)) {
                secondInput = "exit"; // 종료
                return;
            }

            secondInput = input; // 유효한 입력을 설정
            return;
        }
    }

    // 두 번째 입력값 반환
    public String getSecondInput() {
        return secondInput;
    }

    // 연산 열거형 정의 (기호로 변경)
    public enum Operation {
        ADD("+", (a, b) -> a.doubleValue() + b.doubleValue()),
        SUBTRACT("-", (a, b) -> a.doubleValue() - b.doubleValue()),
        MULTIPLY("*", (a, b) -> a.doubleValue() * b.doubleValue()),
        DIVIDE("/", (a, b) -> {
            if (b.doubleValue() == 0) throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
            return a.doubleValue() / b.doubleValue();
        });

        private final String symbol; // 연산 기호
        private final Calculator calculator;

        Operation(String symbol, Calculator calculator) {
            this.symbol = symbol;
            this.calculator = calculator;
        }

        // 연산 실행 메서드
        public double apply(Number a, Number b) {
            return calculator.calculate(a, b);
        }

        // 해당 기호와 매핑된 Operation 반환
        public static Operation fromSymbol(String symbol) {
            for (Operation op : values()) {
                if (op.symbol.equals(symbol)) {
                    return op;
                }
            }
            throw new IllegalArgumentException("유효하지 않은 연산자입니다. (+, -, *, / 만 허용)");
        }

        // 람다식을 위한 함수형 인터페이스
        @FunctionalInterface
        interface Calculator {
            double calculate(Number a, Number b);
        }
    }

    // 최신 결과 반환
    public double getLastResult() {
        if (results.isEmpty()) {
            return 0; // 결과가 없으면 0 반환
        }
        return results.get(results.size() - 1);
    }

    // 결과 리스트 반환
    public List<Double> getResults() {
        return new ArrayList<>(results); // 원본 보호를 위해 복사본 반환
    }

    // 첫 번째 결과 삭제
    public void removeList() {
        if (!results.isEmpty()) {
            results.remove(0); // 첫 번째 결과 삭제
        }
    }


    // 연산 수행 메서드
    public double calculate(T num1, char operator,T num2) {
        try {
            // 연산자를 Operation으로 변환하여 처리(char값을 String으로 변환)
            Operation operation = Operation.fromSymbol(String.valueOf(operator)
            );
            double result = operation.apply(num1, num2);
            results.add(result); // 결과 저장
            return result;
        } catch (IllegalArgumentException e) {
            System.out.println("연산 중 오류 발생: " + e.getMessage());
            return Double.NaN; // NaN 반환
        }
    }

    // Setter
    public void setThirdInput(Scanner sc) {
        while (true) {
            System.out.println("현재 결과 목록에서 입력값보다 큰 결과를 출력하려면 숫자를, 결과값의 합을 구하고 싶으면 'sum'을 입력하세요. 초기화면으로 돌아가려면 Enter: ");
            String input = sc.nextLine();

            // 초기화면으로 이동
            if (input.isBlank()) {
                System.out.println("초기 화면으로 이동합니다.");
                this.thirdInput = null;
                return;
            }

            // 입력값 검증
            if (!validateInput(input)) {
                System.out.println("유효한 숫자, 'sum', 또는 Enter를 입력하세요.");
                continue; // 다시 입력 대기
            }

            // 결과값 합계 처리 (sum 명령어 확인)
            if (input.equalsIgnoreCase("sum")) {
                sumRange(sc); // 결과 값 합계 처리 메서드 호출
                continue; // 루프 반복
            }

            try {
                // 입력값이 숫자인 경우
                double filterNum = Double.parseDouble(input);

                printBiggerResultsWithForEach(filterNum); // 특정 값보다 큰 결과 출력


            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력하지 않았습니다.");
            }

        }
    }


    // Getter
    public String getThirdInput() {
        return thirdInput;
    }



    // 모든 결과 출력
    public void printResults() {
        System.out.println("저장된 결과 목록:");
        for (int i = 0; i < results.size(); i++) {
            System.out.println((i + 1) + ": " + results.get(i));
        }
    }

    public void printBiggerResultsWithForEach(double num) {
        results.stream()
                .filter(result -> result > num) // 필터링: num보다 큰 값
                .forEach(result -> System.out.println("필터링된 값: " + result)); // 출력
    }

    // 범위의 결과 값 합 구하기
    public double sumResults(int start, int end) {
        try {
            return results.subList(start-1, end).stream()
                    .mapToDouble(Double::doubleValue)
                    .sum();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("유효하지 않은 인덱스 범위입니다. 범위를 확인하세요.");
            return 0.0;
        }
    }
    // 범위 지정하기
    public void sumRange(Scanner sc) {

        try {
            System.out.println("시작값을 입력하세요 : ");
            int start = Integer.parseInt(sc.nextLine());

            System.out.println("끝값을 입력하세요 : ");
            int end = Integer.parseInt(sc.nextLine());

            if (start > end) {
                System.out.println("시작 인덱스는 끝 인덱스보다 작거나 같아야 합니다.");
                return;
            }

            double sum = sumResults(start, end);

            // 출력
            System.out.printf("입력한 범위: [%d, %d]%n", start, end);
            System.out.printf("해당 범위 내의 결과 값 합계: %.2f%n", sum);
        } catch (NumberFormatException e) {
            System.out.println("유효한 숫자를 입력하세요.");
        }

    }

}
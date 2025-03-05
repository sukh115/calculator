package cal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//제네릭 T가 Number를 상속
public class ArithmeticCalculator<T extends Number> {

    private final List<Double> results = new ArrayList<>(); // 결과 값 저장 리스트
    private final Scanner sc = new Scanner(System.in); // 사용자 입력을 위한 Scanner 객체

    // 결과 리스트 반환 ( 원본 보호를 위해 복사본을 반환)
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
            System.out.println("첫 번째 숫자를 입력하세요 ('exit' 입력 시 종료): ");
            String input = sc.nextLine().trim();

            if (isExitCommand((input))) return input; // exit를 입력받았을 때 반환

            if (isValidNumber(input)) return input; // 유효한 숫자인 경우 반환

            System.out.println("올바른 숫자를 입력하세요.");
        }
    }

    // 두 번째 숫자 입력
    public String SecondInput() {
        while (true) {
            System.out.println("두 번째 숫자를 입력하세요 ('exit' 입력 시 종료): ");
            String input = sc.nextLine().trim();

            if (isExitCommand((input))) return input; // exit를 입력받았을 때 반환
            if (isValidNumber(input)) return input; // 유효한 숫자인 경우 반환

            System.out.println("올바른 숫자를 입력하세요.");
        }
    }

    // 연산자 입력
    public char OperatorInput() {
        while (true) {
            System.out.println("연산자를 입력하세요 (+,-,*,/) 또는 'exit' 입력 시 종료: ");
            String input = sc.nextLine().trim();

            if (isExitCommand((input))) return 'e'; // exit를 입력받았을 때 반환(char로 받아서 e)

            if (isValidOperator(input)) return input.charAt(0); // 유요한 연산자일 경우 반환

            System.out.println("올바른 연산자를 입력하세요.");
        }
    }

    // 추가 기능 입력
    public String AdditionalInput() {
        while (true) {
            System.out.println("결과 목록에서 큰 값을 보려면 숫자를 입력하세요.");
            System.out.println("결과값의 합계를 보려면 'sum'을 입력하세요.");
            System.out.println("첫 번째 결과를 삭제하려면 'remove' 입력 하세요.)");
            System.out.println("초기 화면으로 돌아가려면 Enter를 누르세요.");
            System.out.print("입력: ");

            String input = sc.nextLine().trim();

            // 각 조건 유효성 검사
            if (input.isBlank() || input.equalsIgnoreCase("sum") || isValidNumber(input)  || input.equalsIgnoreCase("remove")) return input;

            System.out.println("올바른 숫자 또는 'sum'을 입력하세요.");
        }
    }

    // 삭제 커맨드
    private boolean isExitCommand(String input) {
        return input.equalsIgnoreCase("exit");
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
    // 삭제 유효성 검사
    public boolean isValidRemove(String input) {
        if (input.equalsIgnoreCase("remove")) {
            removeList();
            return true;
        }
        return false;
    }

    // 연산자 유효성 검사
    public boolean isValidOperator(String input) {
        return input.length() == 1 && "+-*/".contains(input);
    }

    // 추가 기능 처리
    public void processAdditionalFunctions(String input) {
        if (input.equalsIgnoreCase("sum")) {
            sumRange(); // 합계 기능 실행
            return;
        }
        if (input.equalsIgnoreCase("remove")) {
            isValidRemove(input);  // 결과 삭제 기능 실행
            return;
        }

        try {
            double filterNum = Double.parseDouble(input);
            printBiggerResultsWithForEach(filterNum); // 특정 값보다 큰 값 출력
        } catch (NumberFormatException e) {
            System.out.println("숫자를 입력하지 않았습니다.");
        }
    }

    // 연산 수행
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

    // 특정 범위의 합계 계산
    public void sumRange() {
        int start, end;

        while (true) {
            System.out.println("시작값을 입력하세요: ");
            String startInput = sc.nextLine().trim();
            // 정수값 유효성 검사
            if (isValidNumber(startInput)) {
                start = Integer.parseInt(startInput);
                break;
            }
            System.out.println("올바른 숫자를 입력하세요.");
        }

        while (true) {
            System.out.println("끝값을 입력하세요: ");
            String endInput = sc.nextLine().trim();
            // 정수값 유효성 검사
            if (isValidNumber(endInput)) {
                end = Integer.parseInt(endInput);
                break;
            }
            System.out.println("올바른 숫자를 입력하세요.");
        }
        // end값이 start보다 커야 하는 조건 추가
        if (start > end) {
            System.out.println("시작 인덱스는 끝 인덱스보다 작거나 같아야 합니다.");
            return;
        }

        // 합계를 계산
        double sum = sumResults(start, end);
        System.out.printf("입력한 범위: [%d, %d]%n해당 범위 내의 결과 값 합계: %.2f%n", start, end, sum);
    }

    // 결과 합계 기능 매서드
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
}

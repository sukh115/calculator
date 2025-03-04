package cal;

import java.util.Scanner;

public class challenge {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArithmeticCalculator<Double> calculator = new ArithmeticCalculator<>();

        while (true) {
            // 첫 번째 숫자 입력
            String firstInput = calculator.FirstInput();
            if (firstInput.equalsIgnoreCase("exit")) break;
            if (firstInput.equalsIgnoreCase("remove")) {
                calculator.removeList();
                continue;
            }

            // 연산자 입력
            char operator = calculator.OperatorInput();
            if (operator == 'e') break; // 'e'는 exit을 의미

            // 두 번째 숫자 입력
            String secondInput = calculator.SecondInput();
            if (secondInput.equalsIgnoreCase("exit")) break;

            // 숫자로 변환
            double num1 = Double.parseDouble(firstInput);
            double num2 = Double.parseDouble(secondInput);

            // 연산 수행 및 결과 저장
            double result = calculator.calculate(num1, operator, num2);

            if (!Double.isNaN(result)) {
                System.out.println("현재 결과 값: " + result);
                System.out.println("전체 결과 내역: " + calculator.getResults());
            }

            // 추가 기능 실행
            String additionalInput = calculator.AdditionalInput();
            if (additionalInput.isBlank()) continue; // Enter 입력 시 초기화면

            calculator.processAdditionalFunctions(additionalInput);
        }

        sc.close();
        System.out.println("계산기를 종료합니다.");
    }
}
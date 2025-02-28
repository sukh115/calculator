package cal;

import java.util.Scanner;

public class challenge {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ArithmeticCalculator 객체 생성
        ArithmeticCalculator<Double> calculator = new ArithmeticCalculator<>();

        while (true) {
            // 첫 번째 숫자 입력
            calculator.setFirstInput(sc);
            if (calculator.getFirstInput().equalsIgnoreCase("exit")) {
                break;
            }

            // 연산자 입력
            calculator.setOperator(sc);
            if (calculator.getOperator() == 'e') { // 종료 처리
                break;
            }

            // 두 번째 숫자 입력
            calculator.setSecondInput(sc);
            if (calculator.getSecondInput().equalsIgnoreCase("exit")) {
                break;
            }

            try {
                // 첫 번째와 두 번째 입력값을 숫자로 변환
                double num1 = Double.parseDouble(calculator.getFirstInput());
                double num2 = Double.parseDouble(calculator.getSecondInput());

                // 계산 수행
                double result = calculator.calculate(num1, calculator.getOperator(), num2);

                // 결과 출력
                if (!Double.isNaN(result)) {
                    System.out.println("현재 결과 값: " + result);
                }
                System.out.println("전체 결과 내역: " + calculator.getResults());

                // 더 큰값과 합계 매서드 호출
                calculator.setThirdInput(sc);

            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자를 입력하세요.");
            }
        }

        // 스캐너 종료
        sc.close();
        System.out.println("계산기를 종료합니다.");
    }
}
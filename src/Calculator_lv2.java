import java.util.Scanner;

public class Calculator_lv2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator calculator = new Calculator();

        while (true) {

            // 첫 번째 숫자 입력
            calculator.setFirstInput(sc);
            if (calculator.getFirstInput().equals("exit")) {
                break;
            }

            // 연산자 입력
            calculator.setOperator(sc);
            if (calculator.getOperator() == 'e') { // 종료 처리
                break;
            }

            // 두 번째 숫자 입력
            calculator.setSecondInput(sc);
            if (calculator.getSecondInput().equals("exit")) {
                break;
            }


            // 숫자 변환 및 계산 수행
            try {
                double num1 = Double.parseDouble(calculator.getSecondInput());
                double num2 = Double.parseDouble(calculator.getSecondInput());

                calculator.calculate(num1, calculator.getOperator(), num2);

                // 결과 출력
                System.out.println("현재 결과 값: " + calculator.getLastResult());
                System.out.println("전체 결과 내역: " + calculator.getResults());
            } catch (NumberFormatException e) {
                System.out.println("올바른 숫자를 입력하세요.");
            }
        }

        sc.close();
        System.out.println("계산기를 종료합니다.");
    }
}
package cal;

//연산을 정의
public enum Operation {
    ADD("+", (a, b) -> a.doubleValue() + b.doubleValue()),
    SUBTRACT("-", (a, b) -> a.doubleValue() - b.doubleValue()),
    MULTIPLY("*", (a, b) -> a.doubleValue() * b.doubleValue()),
    DIVIDE("/", (a, b) -> {
        if (b.doubleValue() == 0) throw new IllegalArgumentException("0으로 나눌 수 없습니다.");
        return a.doubleValue() / b.doubleValue();
    });

    private final String symbol;// 연산을 나타내는 기호
    private final Calculator calculator; // 연산을 수행하는 람다식 인터페이스

    // 연산자를 기반으로 Operation 개체 생성
    Operation(String symbol, Calculator calculator) {
        this.symbol = symbol;
        this.calculator = calculator;
    }

    // 두 숫자에 대해 연산을 수행하는 메서드
    public double apply(Number a, Number b) {
        return calculator.calculate(a, b);
    }

    // 연산 기호를 해당 enum값으로 반환하는 메서드
    public static Operation fromSymbol(String symbol) {
        for (Operation op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        throw new IllegalArgumentException("유효하지 않은 연산자입니다. (+, -, *, / 만 허용)");
    }

    // 계산을 수행하는 람다식 인터페이스
    @FunctionalInterface
    interface Calculator {
        double calculate(Number a, Number b);
    }
}

package cal;

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
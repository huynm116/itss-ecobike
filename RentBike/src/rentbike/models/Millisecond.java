package rentbike.models;

public class Millisecond {
    private long value;

    public Millisecond(long value) {
        this.value = value;
        validate();
    }

    public static Millisecond now() {
        return new Millisecond(System.currentTimeMillis());
    }

    public static Millisecond of(long value) {
        return new Millisecond(value);
    }

    public static Millisecond max(Millisecond a, Millisecond b) {
        if (a.isAfter(b)) return a;
        return b;
    }

    private void validate() throws RuntimeException {
        if (value < 0)
            throw new RuntimeException("Millsecond must be >= 0");
    }

    public boolean isAfter(Millisecond that) {
        return value > that.value;
    }

    public long asLong() {
        return value;
    }
}


public class Pair<FIRST, SECOND> implements Comparable<Pair<FIRST, SECOND>> {

    public final FIRST first;
    public final SECOND second;

    private Pair(FIRST first, SECOND second) {
        this.first = first;
        this.second = second;
    }


    @Override
    public int hashCode() {
        return 31 * hashcode(first) + hashcode(second);
    }

    // todo move this to a helper class.
    private static int hashcode(Object o) {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair))
            return false;
        if (this == obj)
            return true;
        return equal(first, ((Pair) obj).first)
                && equal(second, ((Pair) obj).second);
    }

    // todo move this to a helper class.
    private boolean equal(Object o1, Object o2) {
        return o1 == null ? o2 == null : (o1 == o2 || o1.equals(o2));
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ')';
    }


	@Override
	public int compareTo(Pair<FIRST, SECOND> arg0) {
		// TODO Auto-generated method stub
		return 0;
	}
}
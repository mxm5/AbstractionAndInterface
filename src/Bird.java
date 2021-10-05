public abstract class Bird extends Creature {

    @Override
    public void live() {
        System.out.println("living");
    }

    public abstract void fly();

}

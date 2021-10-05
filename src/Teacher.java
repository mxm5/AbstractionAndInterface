public class Teacher extends SpeakingCreature{
    @Override
    public void live() {
        System.out.println("Teacher living");
    }

    @Override
    public void speak() {
        System.out.println("Teacher speaking");
    }
}

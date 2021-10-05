public class TalkingBird extends SpeakingCreature{
    @Override
    public void live() {
        System.out.println("living");
    }

    @Override
    public void speak() {
        System.out.println("hello");
    }
}

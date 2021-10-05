package ThePackageB;

public class Main {
    public static void main(String[] args) {
        Object o = new Object();
        Animal a = new Animal();
        Animal x = new Cat();
        Cat c = new Cat();
        Dog d = new Dog();
        o = a ;
        o = c;
//        a = o ;
        a = c ;
//        c = o;
//        c = a;
//        c = d ;
        c = (Cat) x;
//        d = (Dog) x;
    }
}

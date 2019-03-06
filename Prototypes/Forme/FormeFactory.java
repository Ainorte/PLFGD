public class FormeFactory {

    public Point[] make(List<Object> forme) {
        String formeType = forme.get(0);
        switch(formeType) {
            case "segment":
                return new Segment((Point)forme.get(1), (Point)forme.get(2)).make();
            case "rect":
                return new Rectangle((Point)forme.get(1),(double)forme.get(2),(double)forme.get(3),(double)forme.get(4)).make();
            case "carre":
                return new Carre((Point)forme.get(1),(double)forme.get(2),(double)forme.get(3)).make();
            case "triangle":
                return new Triangle().make();
            case "ellipse":
                return new Ellipse().make();
            case "cercle":
                return new Cercle().make();
            default:
                System.out.println("error not such form");
        }
    }
}
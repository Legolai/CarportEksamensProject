package dk.cphbusiness.dat.carporteksamensproject.model.services;

public class SVG {
    StringBuilder svgBuilder;
    private int x;
    private int y;
    private String viewBox;
    private String width;
    private String height;

    private final int defaultDashDistance;
    private final int defaultDashSpacing;
    private boolean hasStartArrow;
    private boolean hasEndArrow;
    private static final String HEADER_TEMPLATE = "<svg height=\"%s\" width=\"%s\" x=\"%s\" y=\"%s\" viewBox=\"%s\" preserveAspectRatio=\"xMinYMin\">";
    private static final String DOT_RECT_TEMPLATE = "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" stroke=\"black\" stroke-width=\"%d\" stroke-dasharray=\"%d %d\" fill=\"White\" />";
    private static final String DOT_LINE_TEMPLATE = "<Line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"black\" stroke-width=\"%d\" stroke-dasharray=\"%d %d\" style=\"%s\"/>";
    private static final String TEXT_TEMPLATE = "<text style=\"text-anchor: middle\" transform=\"translate(%d,%d) rotate(%d)\">%s</text>";
    private static final String START_ARROW_DEFINITION = """
            <defs>
                <marker id="beginArrow" markerWidth="12" markerHeight="12" refX="0" refY="6" orient="auto">
                    <path d="M0,6 L12,0 L12,12 L0,6" style="fill: #000000;" />
                </marker>
            </defs>
            """;
    private static final String END_ARROW_DEFINITION = """
            <defs>
                <marker id="endArrow" markerWidth="12" markerHeight="12" refX="12" refY="6" orient="auto">
                    <path d="M0,0 L12,6 L0,12 L0,0 " style="fill: #000000;" />
                </marker>
            </defs>""";
    public SVG(int x, int y, String viewBox, String width, String height) {
        this.x = x;
        this.y = y;
        this.viewBox = viewBox;
        this.width = width;
        this.height = height;
        this.hasStartArrow = false;
        this.hasEndArrow = false;
        this.defaultDashDistance = 10;
        this.defaultDashSpacing = 5;
        svgBuilder = new StringBuilder(String.format(HEADER_TEMPLATE, height, width, x, y, viewBox));
    }

    public SVG addRect(int x, int y, int width, int height, int stroke) {
        svgBuilder.append(String.format(DOT_RECT_TEMPLATE, x, y, width, height, stroke, 0, 0));
        return this;
    }

    public SVG addDottedRect(int x, int y, int width, int height, int stroke) {
        svgBuilder.append(String.format(DOT_RECT_TEMPLATE, x, y, width, height, stroke, defaultDashDistance, defaultDashSpacing));
        return this;
    }

    public SVG addDottedLine(int x1, int y1, int x2, int y2, int stroke) {
        svgBuilder.append(String.format(DOT_LINE_TEMPLATE, x1, y1, x2, y2, stroke, defaultDashDistance, defaultDashSpacing, ""));
        return this;
    }

    public SVG addLine(int x1, int y1, int x2, int y2, int stroke) {
        svgBuilder.append(String.format(DOT_LINE_TEMPLATE, x1, y1, x2, y2, stroke, 0, 0, ""));
        return this;
    }

    public SVG addEndArrow(int x1, int y1, int x2, int y2, int stroke) {
        if (!hasEndArrow){
            svgBuilder.append(END_ARROW_DEFINITION);
            hasEndArrow = true;
        }

        svgBuilder.append(String.format(DOT_LINE_TEMPLATE, x1, y1, x2, y2, stroke, 0, 0, "marker-end: url(#endArrow);"));
        return this;
    }

    public SVG addStartArrow(int x1, int y1, int x2, int y2, int stroke) {
        if(!hasStartArrow) {
            svgBuilder.append(START_ARROW_DEFINITION);
            hasStartArrow = true;
        }
        svgBuilder.append(String.format(DOT_LINE_TEMPLATE, x1, y1, x2, y2, stroke, 0, 0, "marker-start: url(#startArrow);"));
        return this;
    }

    public SVG addDubleArrow(int x1, int y1, int x2, int y2, int stroke) {
        if (!hasEndArrow) {
            svgBuilder.append(END_ARROW_DEFINITION);
            hasEndArrow = true;
        }
        if(!hasStartArrow) {
            svgBuilder.append(START_ARROW_DEFINITION);
            hasStartArrow = true;
        }
        svgBuilder.append(String.format(DOT_LINE_TEMPLATE, x1, y1, x2, y2, stroke, 0, 0, "marker-start: url(#startArrow); marker-end: url(#endArrow);"));
        return this;
    }

    public SVG addText(String text, int x, int y, int rotation){
        svgBuilder.append(String.format(TEXT_TEMPLATE, x, y, rotation, text));
        return this;
    }

    public SVG addSVG(SVG innerSVG) {
        svgBuilder.append(innerSVG.toString());
        return this;
    }

    @Override
    public String toString() {
        return svgBuilder.append("</svg>").toString();
    }
}

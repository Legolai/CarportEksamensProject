package dk.cphbusiness.dat.carporteksamensproject.model.services;

public class SVG {
    private static final String HEADER_TEMPLATE = "<svg height=\"%s\" width=\"%s\" x=\"%s\" y=\"%s\" viewBox=\"%s\" preserveAspectRatio=\"xMinYMin\">";
    private static final String DOT_RECT_TEMPLATE = "<rect x=\"%d\" y=\"%d\" width=\"%d\" height=\"%d\" stroke=\"black\" stroke-width=\"%d\" stroke-dasharray=\"%d %d\" fill=\"White\" />";
    private static final String DOT_LINE_TEMPLATE = "<Line x1=\"%d\" y1=\"%d\" x2=\"%d\" y2=\"%d\" stroke=\"black\" stroke-width=\"%d\" stroke-dasharray=\"%d %d\" style=\"%s\"/>";
    private static final String TEXT_TEMPLATE = "<text style=\"text-anchor: middle\" font-size=\"%s\" transform=\"translate(%d,%d) rotate(%d)\">%s</text>";
    private static final String START_ARROW_DEFINITION = """
            <defs>
                <marker id="startArrow" markerWidth="6" markerHeight="6" refX="0" refY="2" orient="auto">
                    <polygon points="4 0, 4 4, 0 2" fill="black" />
                </marker>
            </defs>""";
    private static final String END_ARROW_DEFINITION = """
            <defs>
                <marker id="endArrow" markerWidth="10" markerHeight="7" refX="4" refY="2" orient="auto">
                    <polygon points="0 0, 4 2, 0 4" fill="black" />
                </marker>
            </defs>""";
    private final int x;
    private final int y;
    private final String viewBox;
    private final String width;
    private final String height;
    private final int defaultDashDistance;
    private final int defaultDashSpacing;
    StringBuilder svgBuilder;
    private boolean hasStartArrow;
    private boolean hasEndArrow;
    private String textStyle = "14px";

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
        if (!hasEndArrow) {
            svgBuilder.append(END_ARROW_DEFINITION);
            hasEndArrow = true;
        }

        svgBuilder.append(String.format(DOT_LINE_TEMPLATE, x1, y1, x2, y2, stroke, 0, 0, "marker-end: url(#endArrow);"));
        return this;
    }

    public SVG addStartArrow(int x1, int y1, int x2, int y2, int stroke) {
        if (!hasStartArrow) {
            svgBuilder.append(START_ARROW_DEFINITION);
            hasStartArrow = true;
        }
        svgBuilder.append(String.format(DOT_LINE_TEMPLATE, x1, y1, x2, y2, stroke, 0, 0, "marker-start: url(#startArrow);"));
        return this;
    }

    public SVG addDoubleArrow(int x1, int y1, int x2, int y2, int stroke) {
        if (!hasEndArrow) {
            svgBuilder.append(END_ARROW_DEFINITION);
            hasEndArrow = true;
        }
        if (!hasStartArrow) {
            svgBuilder.append(START_ARROW_DEFINITION);
            hasStartArrow = true;
        }
        svgBuilder.append(String.format(DOT_LINE_TEMPLATE, x1, y1, x2, y2, stroke, 0, 0, "marker-start: url(#startArrow); marker-end: url(#endArrow);"));
        return this;
    }

    public SVG addText(String text, int x, int y, int rotation) {
        svgBuilder.append(String.format(TEXT_TEMPLATE, textStyle, x, y, rotation, text));
        return this;
    }

    public void setTextStyle(String textStyle) {
        this.textStyle = textStyle;
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

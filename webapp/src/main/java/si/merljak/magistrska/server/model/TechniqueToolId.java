package si.merljak.magistrska.server.model;

import java.io.Serializable;

public class TechniqueToolId implements Serializable {

	private static final long serialVersionUID = -943744955327407997L;

	public Technique technique;
	public Tool tool;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TechniqueToolId other = (TechniqueToolId) o;
        return technique.equals(other.technique) && tool.equals(other.tool);
    }
}
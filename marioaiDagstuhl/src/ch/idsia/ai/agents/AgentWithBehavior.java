package ch.idsia.ai.agents;

import ch.idsia.ai.agents.Agent;
import ch.idsia.ai.agents.Behavior;
import org.omg.CosNaming.BindingHelper;

public abstract class AgentWithBehavior implements Agent {
    Behavior behavior = new Behavior();


    public abstract Behavior getBehavior();

    public void clearBehavior() {
        this.behavior.clear();
    }
}

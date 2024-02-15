package gay.lemmaeof.permet.relics.trigger;

public class HitTrigger implements Trigger {

	@Override
	public boolean test(TriggerContext t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'test'");
	}
    // trigger activated on hitting entity or block
    // should be filtered based on type
    /*
     triggers: [
        {
            "type": "hit",
            "condition": { // trigger condition predicate
                "type": "entity_hit" // could be and, or, xor etc for special logic
            }
        }
     ]
     */
}

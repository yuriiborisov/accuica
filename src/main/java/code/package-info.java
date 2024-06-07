@TypeDef(
        name = "json", typeClass = JsonType.class
)
package code;
/**
 * это необходимо для json полей в базе
 */

import io.hypersistence.utils.hibernate.type.json.JsonType;
import org.hibernate.annotations.TypeDef;
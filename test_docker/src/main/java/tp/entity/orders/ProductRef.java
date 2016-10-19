package tp.entity.orders;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//------ lombok generation code annotations ------
@Getter @Setter
@ToString()
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//------------------------------------------------

@Entity
@Table(name="orders.ProductRef")
public class ProductRef {
	
	@Id
	private Long productId;
	
    private String label ;
    private double price;

}

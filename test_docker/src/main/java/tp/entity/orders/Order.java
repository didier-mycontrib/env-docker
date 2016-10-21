package tp.entity.orders;

import java.util.Date;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//------ lombok generation code annotations ------
@Getter @Setter
@ToString(exclude={"orderLines"})
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//------------------------------------------------

@Entity
@Table(name="Order")
public class Order {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long orderId;
	
	@Temporal(TemporalType.DATE)
    private Date orderDate;
	
    private Long cutomerId;
    
    private double totalPrice;
    
    @OneToMany(cascade=CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name="orderId")
    @MapKeyColumn (name="lineNumber")
    private Map<Integer,OrderLine> orderLines;

}

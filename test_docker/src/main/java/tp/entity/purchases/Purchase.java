package tp.entity.purchases;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@ToString()
//@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
//------------------------------------------------

@Entity
@Table(name="Purchase")
public class Purchase {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long purchaseId ;
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date purchaseDateTime;
    
    private Long cutomerId;
    
    private double amount;

}

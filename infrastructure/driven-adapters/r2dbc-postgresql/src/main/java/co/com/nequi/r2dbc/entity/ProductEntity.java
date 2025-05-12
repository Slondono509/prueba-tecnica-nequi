package co.com.nequi.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(schema = "public", value = "products")
public class ProductEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("stock")
    private Integer stock;

    @Column("branch_id")
    private Long branchId;
} 
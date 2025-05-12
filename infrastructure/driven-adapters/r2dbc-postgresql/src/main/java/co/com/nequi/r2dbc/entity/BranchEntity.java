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
@Table(schema = "public", value = "branches")
public class BranchEntity {

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("franchise_id")
    private Long franchiseId;
} 
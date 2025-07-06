package com.projectmanagement.repository;

import com.projectmanagement.model.OrganizationModel;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface OrganizationRepository extends JpaRepository<OrganizationModel, UUID> {

    @Query("SELECT o FROM OrganizationModel o WHERE o.id = :id AND o.active = 'Y'")
    OrganizationModel findFirstById(String id);

    @Modifying
    @Transactional
    @Query("UPDATE OrganizationModel o SET o.name = :#{#organizationModel.name}, o.industry = :#{#organizationModel.industry}, o.domain = :#{#organizationModel.domain}, o.address = :#{#organizationModel.address}, o.plan = :#{#organizationModel.plan}, o.lastUpdatedAt = CURRENT_TIMESTAMP, o.lastUpdatedBy = :#{#organizationModel.lastUpdatedBy}  WHERE o.id = :id AND o.active = 'Y' ")
    int updateOrganization(String id, OrganizationModel organizationModel);

    @Modifying
    @Transactional
    @Query("UPDATE OrganizationModel o SET o.active = 'D' WHERE o.id = :id AND o.active = 'Y' ")
    int deleteOrganization(String id);

}

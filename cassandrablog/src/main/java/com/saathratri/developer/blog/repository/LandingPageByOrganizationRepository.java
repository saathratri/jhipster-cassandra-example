/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

package com.saathratri.developer.blog.repository;

import com.saathratri.developer.blog.domain.LandingPageByOrganization;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the LandingPageByOrganization entity.
 */
@Repository
public interface LandingPageByOrganizationRepository extends CassandraRepository<LandingPageByOrganization, UUID> {}

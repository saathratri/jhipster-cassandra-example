/*
 * Copyright (c) 2025-2026 Saathratri, LLC. All rights reserved.
 * SPDX-License-Identifier: LicenseRef-Saathratri-Proprietary
 * Proprietary and confidential - see LICENSE in the repository root.
 */

package com.saathratri.developer.store.repository;

import com.saathratri.developer.store.domain.Report;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data Cassandra repository for the Report entity.
 */
@Repository
public interface ReportRepository extends CassandraRepository<Report, UUID> {}

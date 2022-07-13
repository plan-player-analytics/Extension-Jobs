/*
    Copyright(c) 2019 AuroraLS3

    The MIT License(MIT)

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files(the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions :
    The above copyright notice and this permission notice shall be included in
    all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
    THE SOFTWARE.
*/
package net.playeranalytics.extension.jobs;

import com.djrapitops.plan.extension.DataExtension;
import com.djrapitops.plan.extension.annotation.GroupProvider;
import com.djrapitops.plan.extension.annotation.PluginInfo;
import com.djrapitops.plan.extension.annotation.TableProvider;
import com.djrapitops.plan.extension.icon.Color;
import com.djrapitops.plan.extension.icon.Family;
import com.djrapitops.plan.extension.icon.Icon;
import com.djrapitops.plan.extension.table.Table;
import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.dao.JobsDAO;
import com.gamingmesh.jobs.dao.JobsDAOData;

import java.util.List;
import java.util.UUID;

/**
 * Jobs DataExtension.
 *
 * @author AuroraLS3
 */
@PluginInfo(name = "Jobs", iconName = "suitcase", iconFamily = Family.SOLID, color = Color.BROWN)
public class JobsExtension implements DataExtension {

    public JobsExtension() {
    }

    private JobsDAO getDB() {
        return Jobs.getDBManager().getDB();
    }

    @GroupProvider(
            text = "Job",
            iconName = "suitcase",
            groupColor = Color.BROWN
    )
    public String[] jobs(UUID playerUUID) {
        List<JobsDAOData> jobs = getDB().getAllJobs(null, playerUUID);
        if (jobs.isEmpty()) return new String[]{"No Job"};
        return jobs.stream()
                .map(JobsDAOData::getJobName)
                .toArray(String[]::new);
    }

    @TableProvider(tableColor = Color.BROWN)
    public Table jobLevelTable(UUID playerUUID) {
        Table.Factory table = Table.builder()
                .columnOne("Job", Icon.called("suitcase").build())
                .columnTwo("Level", Icon.called("plus").build());

        List<JobsDAOData> jobs = getDB().getAllJobs(null, playerUUID);
        for (JobsDAOData job : jobs) {
            table.addRow(job.getJobName(), job.getLevel());
        }

        return table.build();
    }
}
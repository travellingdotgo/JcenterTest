package com.bewant2be.doit.jcentertest.bean;

import java.util.List;

/**
 * Created by user on 12/14/17.
 */

public class JsonBean {

    private List<MiddleSchoolsBean> MiddleSchools;

    public List<MiddleSchoolsBean> getMiddleSchools() {
        return MiddleSchools;
    }

    public void setMiddleSchools(List<MiddleSchoolsBean> MiddleSchools) {
        this.MiddleSchools = MiddleSchools;
    }

    public static class MiddleSchoolsBean {
        /**
         * SchoolName : 吉大附中
         * ID : sch_001
         * Departments : [{"DepartmentName":"财务部","DepartmentId":"bm_001"},{"DepartmentName":"事业部","DepartmentId":"bm_002"}]
         */

        private String SchoolName;
        private String ID;
        private List<DepartmentsBean> Departments;

        public String getSchoolName() {
            return SchoolName;
        }

        public void setSchoolName(String SchoolName) {
            this.SchoolName = SchoolName;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public List<DepartmentsBean> getDepartments() {
            return Departments;
        }

        public void setDepartments(List<DepartmentsBean> Departments) {
            this.Departments = Departments;
        }

        public static class DepartmentsBean {
            /**
             * DepartmentName : 财务部
             * DepartmentId : bm_001
             */

            private String DepartmentName;
            private String DepartmentId;

            public String getDepartmentName() {
                return DepartmentName;
            }

            public void setDepartmentName(String DepartmentName) {
                this.DepartmentName = DepartmentName;
            }

            public String getDepartmentId() {
                return DepartmentId;
            }

            public void setDepartmentId(String DepartmentId) {
                this.DepartmentId = DepartmentId;
            }
        }
    }
}

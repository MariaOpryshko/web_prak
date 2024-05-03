package ru.java.project.DAO.implementation;


import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import ru.java.project.DAO.interfaces.EmployeeDAO;
import ru.java.project.bd_classes.basic.Employee;
import ru.java.project.bd_classes.basic.Project;


import java.util.List;

@Repository
public class EmployeeDAOImpl extends CommonDAOImpl<Employee, Long> implements EmployeeDAO {

    public EmployeeDAOImpl(){
        super(Employee.class);
    }

    @Override
    public List<Employee> getEmployeeByName(String name){
        try (Session session = sessionFactory.openSession()) {
            Query<Employee> query = session.createQuery("FROM Employee WHERE full_name LIKE :Name", Employee.class)
                    .setParameter("Name", "%" + name + "%");
            return query.getResultList().isEmpty() ? null : query.getResultList();
        }
    }


}

import { useNavigate } from "react-router-dom";
import { UserCard } from "../UserCard/UserCard";
import { useGetAllUsersQuery } from "../../services/userApi";
import styles from "./UsersGrid.module.scss";

export const UsersGrid = () => {
    const navigate = useNavigate();
    const { data: users, error, isLoading, isFetching, currentData } = useGetAllUsersQuery();
    console.log(users)

    const handleUserClick = (id: number) => {
        navigate(`/users/${id}`);
    };
    return (
        <div className={styles["users-grid-container"]}>
            <h1>Users List</h1>
            <div className={styles["users-grid"]}>

                {isLoading && <p>Loading...</p>}
                {isFetching && currentData && <p>Fetching from Cache...</p>}
                {error && <p>Error fetching users</p>}
                {users && (
                    users.users.length === 0 ? (
                        <p>No Users found</p>
                    ) : (
                        users.users.map((user) => (
                            <UserCard
                                key={user.id}
                                user={user}
                                onClick={() => handleUserClick(user.id)}
                            />
                        ))
                    )
                )}
            </div>

        </div>
    )
}

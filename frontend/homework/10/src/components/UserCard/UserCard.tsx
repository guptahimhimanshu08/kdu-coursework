import type { User } from "../../types/User";
import styles from "./UserCard.module.scss";
interface UserCardProps {
    user: User;
    onClick: () => void;
}
export const UserCard = ({ user, onClick }: UserCardProps) => {
    return (
        <div onClick={onClick} className={styles["user-card"]}>
                <div>
                    <img src={user.avatar} alt={`${user.firstName} ${user.lastName}`} />
                </div>
                <div className={styles["user-card-user-info"]}>
                    <h3>{user.firstName} {user.lastName}</h3>
                    <p>{user.email}</p>
                    <p>{user.phone}</p>
                    <p>Age:{user.age}</p>
                </div>
        </div>
    )
}
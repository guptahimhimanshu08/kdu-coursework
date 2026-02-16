import { Header } from "../../components/Header/Header";
import { Form } from "../../components/Form/Form";
import styles from "./RegistrationPage.module.scss";

export const RegistrationPage = () => {
    return (
        <div className={styles.registrationPage}>
            <Header />
            <Form />
        </div>
    );
};

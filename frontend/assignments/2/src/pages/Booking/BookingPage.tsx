import styles from './BookingPage.module.scss';
import { useGetConfigQuery } from '../../services/configApi';
import Loader from '../../components/loader/Loader';
import { Header } from '../../components/Header/Header';
import { PreferencesSection } from '../../components/PreferencesSection/PreferencesSection';
import { SummarySection } from '../../components/SummarySection/SummarySection';


export default function BookingPage() {
    const {data, isLoading, error} = useGetConfigQuery()

    
    console.log(data)
    if(isLoading) return <Loader />
    if (error || !data) return <div>Failed to load configuration.</div>;

    return (

        // navbar : sticky

        <div className={styles.booking_page}>
           <Header />
            <div className={styles.booking_page_grid}>
                
                <PreferencesSection />

                <SummarySection />
            </div>
        </div>

    )
}
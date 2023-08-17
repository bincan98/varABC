import {Outlet} from 'react-router-dom';
import { SideBar } from '../../components/common/sidebar';

export const MyPage = () => {
  return (
    <>
      <div className='flex flex-row w-full h-min-screen bg-bg2 bg-cover bg-repeat-y'>
        <SideBar />
        <Outlet className />
      </div>
    </>
  );
};
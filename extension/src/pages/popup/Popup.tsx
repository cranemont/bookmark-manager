import { css } from "@emotion/react";
import styled from "@emotion/styled";
import setting from "@assets/img/setting.svg";
import profile from "@assets/img/profile.svg";
import bookmark from "@assets/img/bookmark.svg";
import sync from "@assets/img/sync.svg";
import backup from "@assets/img/backup.svg";

const PopupContainer = styled.div`
  width: 200px;
  min-height: 100px;
`;

const Container = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  min-height: 40px;
  gap: 8px;
`;

const Name = styled.div`
  font-size: 16px;
`;

const SettingIcon = styled.img`
  width: 20px;
  height: 20px;
`;

const SettingButton = styled.a`
  cursor: pointer;
`;

const ProfileContainer = styled.div`
  display: flex;
  flex-direction: row;
  align-items: end;
  gap: 8px;
`;

const ProfileImg = styled.img`
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background-color: #becccc;
`;

const Icon = styled.img`
  width: 28px;
  height: 28px;
  filter: invert(100%);
`;

const IconButton = styled.a`
  width: 100%;
  height: 40px;
  background-color: #1abc9b;
  border-radius: 12px;
  cursor: pointer;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Popup = () => {
  return (
    <PopupContainer>
      <Container>
        <ProfileContainer>
          <ProfileImg src={profile} />
          <Name>홍길동</Name>
        </ProfileContainer>
        <SettingButton>
          <SettingIcon src={setting} alt="setting button" />
        </SettingButton>
      </Container>
      <Container css={css({ marginTop: "12px" })}>
        <IconButton>
          <Icon src={bookmark} />
        </IconButton>
        <IconButton>
          <Icon src={sync} />
        </IconButton>
        <IconButton>
          <Icon src={backup} />
        </IconButton>
      </Container>
    </PopupContainer>
  );
};

export default Popup;

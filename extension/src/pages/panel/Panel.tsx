import {
  Avatar,
  Box,
  Button,
  ButtonProps,
  Card,
  CardBody,
  CardFooter,
  CardProps,
  ChakraProvider,
  DarkMode,
  Divider,
  Flex,
  FlexProps,
  Grid,
  GridItem,
  Heading,
  HeadingProps,
  Img,
  Link,
  SimpleGrid,
  Stack,
  Tag,
  TagLabel,
  Text,
  useDisclosure,
} from "@chakra-ui/react";
import { faFolder } from "@fortawesome/free-regular-svg-icons";
import {
  faEdit,
  faGear,
  faSearch,
  faTrash,
} from "@fortawesome/free-solid-svg-icons";
import { FaIcon } from "@src/components/common/FaIcon";
import theme from "@src/components/common/GlobalTheme";
import { PanelBookmarkEditModal } from "@src/components/panel/PanelBookmarkEditModal";
import { PanelSearchModal } from "@src/components/panel/PanelSearchModal";
import axios from "axios";
import { useEffect, useState } from "react";
import logo from "../../assets/img/logo.png";

const Title = (props: FlexProps) => (
  <DarkMode>
    <Flex
      alignItems="center"
      justifyContent="center"
      gap={2}
      height={16}
      cursor="default"
      fontSize="xl"
      fontWeight="bold"
      bg={"gray.700"}
      color={"white"}
      {...props}
    >
      {props.children}
    </Flex>
  </DarkMode>
);

const Login = (props: { user: any }) => (
  <Flex>
    {props.user ? (
      <Flex
        width={"100%"}
        alignItems="center"
        justifyContent="flex-start"
        gap={2}
        height={14}
        paddingX={4}
        bg={"gray.600"}
        borderRadius={"md"}
      >
        <Avatar size={"xs"} bg="gray.500" />
        <Text fontWeight={"bold"} noOfLines={1}>
          {props.user?.username}
        </Text>
        {/*<Button>로그아웃</Button>*/}
      </Flex>
    ) : (
      <Button
        as={Flex}
        height={14}
        width={"100%"}
        alignItems="center"
        justifyContent="flex-start"
        gap={2}
        cursor={"pointer"}
      >
        <Avatar size={"xs"} bg="gray.500" />
        <Text fontWeight={"bold"} noOfLines={1}>
          로그인
        </Text>
      </Button>
    )}
  </Flex>
);

const SectionContainer = (props: FlexProps) => (
  <Flex
    boxSizing={"border-box"}
    flexDirection={"column"}
    paddingY={5}
    paddingX={4}
    sx={{
      "& > *": {
        fontSize: "md",
      },
    }}
    {...props}
  >
    {props.children}
  </Flex>
);

const SectionTitle = (props: HeadingProps) => (
  <Heading
    fontSize={"md"}
    fontWeight={"bold"}
    paddingX={4}
    paddingY={4}
    {...props}
  >
    {props.children}
  </Heading>
);

const SectionIconBtn = (props: ButtonProps) => (
  <Button justifyContent={"flex-start"} variant={"ghost"} {...props}>
    {props.children}
  </Button>
);

const TagContainer = (props: FlexProps) => (
  <Flex
    flexDirection={"row"}
    alignItems={"center"}
    justifyContent={"flex-start"}
    gap={2}
    {...props}
  >
    {props.children}
  </Flex>
);

const BookmarkCard = (
  props: CardProps & { bookmark: any; edit: any; delete: any }
) => (
  <Card width={"100%"} {...props}>
    <CardBody>
      {/*<Img*/}
      {/*  src="https://images.unsplash.com/photo-1555041469-a586c61ea9bc?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1770&q=80"*/}
      {/*  alt="Green double couch with wooden legs"*/}
      {/*  borderRadius="lg"*/}
      {/*/>*/}
      <Stack spacing={4}>
        <Heading size="sm">{props.bookmark.title}</Heading>
        <Text
          as={Link}
          size="xs"
          mt="2 !important"
          noOfLines={1}
          href={props.bookmark.url}
          target="_blank"
        >
          {props.bookmark.url}
        </Text>
        <Divider />
        <Heading size={"xs"}>
          <Flex alignItems={"center"} gap={2}>
            <FaIcon icon={faFolder} /> {props.bookmark.group}
          </Flex>
        </Heading>
        <Box
          overflowX={"scroll"}
          sx={{ "::-webkit-scrollbar": { display: "none" } }}
        >
          <TagContainer flexWrap={"wrap"}>
            {props.bookmark.tags.map((value, index) => (
              <Tag key={index}>{value}</Tag>
            ))}
          </TagContainer>
        </Box>
        <Text>{props.bookmark.summary}</Text>
      </Stack>
    </CardBody>
    <CardFooter
      justify="space-between"
      gap={4}
      sx={{
        "& > button": {
          minW: "136px",
        },
      }}
    >
      <Button minWidth="0 !important" flex="1" onClick={props.edit}>
        <FaIcon icon={faEdit} />
      </Button>
      <Button minWidth="0 !important" flex="1" onClick={props.delete}>
        <FaIcon icon={faTrash} />
      </Button>
    </CardFooter>
  </Card>
);

const Panel = () => {
  const myAxios = axios.create({
    baseURL: "https://nother.ml",
    headers: {
      "Content-Type": "application/json",
    },
    withCredentials: true,
  });

  const [user, setUser] = useState(null);
  const [groups, setGroups] = useState([]);
  const [tags, setTags] = useState([]);
  const [bookmarks, setBookmarks] = useState([]);
  const [selected, setSelected] = useState(null);
  const [filter, setFilter] = useState({ type: null, value: "All" });

  const {
    isOpen: isSearchOpen,
    onOpen: onSearchOpen,
    onClose: onSearchClose,
  } = useDisclosure();
  const {
    isOpen: isEditOpen,
    onOpen: onEditOpen,
    onClose: onEditClose,
  } = useDisclosure();

  const init = async () => {
    // check user
    const cookie = await chrome.cookies.get({
      url: "https://nother.ml",
      name: "connect.sid",
    });
    if (cookie) {
      const { status, message, user } = await chrome.runtime.sendMessage({
        type: "get-user",
      });
      setUser(user);
    }

    // request APIs
    const [groupsRes, tagsRes] = await Promise.allSettled([
      myAxios.get("groups"),
      myAxios.get("bookmark/tags"),
    ]);

    // update UI
    if (groupsRes.status == "rejected") {
      console.log("group err");
    } else {
      groupsRes.value.data && setGroups(groupsRes.value.data);
    }

    if (tagsRes.status == "rejected") {
      console.log("tags err");
    } else {
      tagsRes.value.data && setTags(tagsRes.value.data);
    }
  };

  const updateBookmarks = async () => {
    switch (filter.type) {
      case null: {
        const res = await myAxios.get("bookmarks");
        setBookmarks(res.data);
        break;
      }
      case "search": {
        const res = await myAxios.get(`bookmarks/search?query=${filter.value}`);
        setBookmarks(res.data);
        break;
      }
      case "tag": {
        const res = await myAxios.get(`bookmarks/tag?names=${filter.value}`);
        setBookmarks(res.data);
        break;
      }
      case "group": {
        const res = await myAxios.get(`bookmarks/group?name=${filter.value}`);
        setBookmarks(res.data);
        break;
      }
    }
  };

  const onBookmarkEdit = async (bookmark) => {
    setSelected(bookmark);
    onEditOpen();
  };

  const onBookmarkDelete = async (id: string) => {
    await myAxios.delete(`bookmark/${id}`);
    await updateBookmarks();
  };

  const updateBookmark = async (id, updateBody) => {
    await myAxios.put(`bookmark/${id}`, updateBody);
  };

  const toggleTagFilter = (value) => {
    if (filter.type !== "tag" || filter.value === "") {
      setFilter({ type: "tag", value: value });
    } else {
      const tags = filter.value.split(",");
      if (tags.includes(value)) {
        if (tags.length > 1) {
          setFilter({
            type: "tag",
            value: tags.filter((t) => t != value).join(","),
          });
        }
      } else {
        setFilter({ type: "tag", value: tags.concat([value]).join(",") });
      }
    }
  };

  useEffect(() => {
    init();
  }, []);

  useEffect(() => {
    updateBookmarks();
  }, [filter]);

  useEffect(() => {
    if (isEditOpen == false) {
      updateBookmarks();
    }
  }, [isEditOpen]);

  return (
    <ChakraProvider theme={theme}>
      <PanelSearchModal
        isOpen={isSearchOpen}
        onClose={onSearchClose}
        search={(query: string) => setFilter({ type: "search", value: query })}
      />
      <PanelBookmarkEditModal
        isOpen={isEditOpen}
        onClose={onEditClose}
        bookmark={selected}
        update={updateBookmark}
        groups={groups}
      />
      <Grid gridTemplateColumns="320px auto" minHeight="100vh">
        <GridItem
          height={"100vh"}
          display="flex"
          flexDirection="column"
          bg={"gray.600"}
          color={"gray.50"}
        >
          <Title>
            <Img src={logo} height={6} />
            Bookmarker
          </Title>
          <Flex flexDirection="column" height={"100%"} overflowY={"scroll"}>
            <SectionContainer>
              <Login user={user} />
              <SectionIconBtn
                leftIcon={<FaIcon icon={faSearch} />}
                onClick={onSearchOpen}
              >
                Search
              </SectionIconBtn>
              <SectionIconBtn leftIcon={<FaIcon icon={faGear} />} isDisabled>
                Settings
              </SectionIconBtn>
            </SectionContainer>
            <SectionContainer>
              <SectionTitle>Groups</SectionTitle>
              {groups.map((value, index) => (
                <SectionIconBtn
                  key={index}
                  leftIcon={<FaIcon icon={faFolder} />}
                  onClick={() => setFilter({ type: "group", value: value })}
                >
                  {value}
                </SectionIconBtn>
              ))}
            </SectionContainer>
            <SectionContainer>
              <SectionTitle>Tags</SectionTitle>
              <TagContainer paddingX={4} paddingY={2} flexWrap={"wrap"}>
                {tags.map((value, index) => (
                  <Tag
                    as={Button}
                    key={index}
                    height={"auto"}
                    onClick={() => toggleTagFilter(value)}
                  >
                    <TagLabel>{value}</TagLabel>
                  </Tag>
                ))}
              </TagContainer>
            </SectionContainer>
          </Flex>
        </GridItem>
        <GridItem
          height={"100vh"}
          width={"100%"}
          display="flex"
          flexDirection="column"
        >
          <Flex
            width={"100%"}
            alignItems={"center"}
            justifyContent={"space-between"}
            padding={6}
            gap={4}
          >
            <Heading size={"lg"}>{filter.value}</Heading>
            {filter.type && (
              <Button
                size={"xs"}
                onClick={() => setFilter({ type: null, value: "All" })}
              >
                Initialize Filter
              </Button>
            )}
          </Flex>
          <SimpleGrid
            minChildWidth="280px"
            spacing={4}
            overflowY={"scroll"}
            paddingX={6}
            pb={6}
          >
            {bookmarks.map((value, index) => (
              <BookmarkCard
                key={index}
                bookmark={value}
                edit={() => onBookmarkEdit(value)}
                delete={() => onBookmarkDelete(value.id)}
              />
            ))}
          </SimpleGrid>
        </GridItem>
      </Grid>
    </ChakraProvider>
  );
};

export default Panel;

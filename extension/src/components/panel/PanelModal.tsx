import {
  Button,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
} from "@chakra-ui/react";

export const PanelModal = ({ isOpen, onClose, ...props }) => {
  return (
    <Modal isOpen={isOpen} onClose={onClose}>
      <ModalOverlay />
      <ModalContent>
        <ModalHeader>Search for bookmarks</ModalHeader>
        <ModalCloseButton />
        <ModalBody pb={6}>
          <Input placeholder="Input keyword here." />
        </ModalBody>

        <ModalFooter>
          <Button colorScheme="blue" mr={3}>
            Search
          </Button>
          <Button onClick={onClose}>Cancel</Button>
        </ModalFooter>
      </ModalContent>
    </Modal>
  );
};
